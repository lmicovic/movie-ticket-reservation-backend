package application.service;

import java.util.List; 
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import application.configuration.exceptions.ProjectionOverlapException;
import application.model.MovieDTO;
import application.model.ProjectionDTO;
import application.model.ReservationDTO;
import application.model.RoomDTO;
import application.repository.MovieRepository;
import application.repository.ProjectionRepository;
import application.repository.RoomRepository;

@Service
public class ProjectionService {

	private ProjectionRepository projectionRepository;
	private MovieRepository movieRepository;
	private RoomRepository roomRepository;
	
	private ReservationService reservationService;
	
	@Autowired
	public ProjectionService(ProjectionRepository projectionRepository, ReservationService reservationService, MovieRepository movieRepository, RoomRepository roomRepository) {
		this.projectionRepository = projectionRepository;
		this.movieRepository = movieRepository;
		this.reservationService = reservationService;
		this.roomRepository = roomRepository;
	}

	public List<ProjectionDTO> getAll() {
		
		List<ProjectionDTO> projections = this.projectionRepository.findAll();
		return projections;
	}

	public ProjectionDTO getById(Long id) {
		
		Optional<ProjectionDTO> projection = this.projectionRepository.findById(id);
		if(projection.isPresent() == false) {
			return null;
		}
		
		return projection.get();
	}

	public ProjectionDTO save(ProjectionDTO object) throws ProjectionOverlapException {
		
		// Get All Projections that are same Day and Same Room as Object
		List<ProjectionDTO> projections = this.projectionRepository.findByRoomAndDate_Day(object.getRoom(), object.getProjectionDate().getDay());
		for (ProjectionDTO projection : projections) {
			if(object.projectionDateOverlap(projection)) {
				throw new ProjectionOverlapException("Current Projection is overlapping Room and Time with other Projection.\n\nCurrentProjection:\nRoom: " + object.getRoom().getName() + "\nTime: " + object.getProjectionDate() + "\n\nProjection:\nRoom: " +  projection.getRoom().getName()+ "\nTime: " + projection.getProjectionDate() + "\n\n\n");
			}
		}
		
		ProjectionDTO projection = this.projectionRepository.save(object);
		return projection;
	}

	@Deprecated
	public ProjectionDTO update(ProjectionDTO object) {
		
		if(this.movieRepository.existsById(object.getId()) == true) {
			try {
				object = this.save(object);
			} catch (ProjectionOverlapException e) {
				e.printStackTrace();
			}
		}
		
		return object;
	}
	

	
	@SuppressWarnings("deprecation")
	public boolean delete(Long projectionId) {
		
		Optional<ProjectionDTO> projection = this.projectionRepository.findById(projectionId);
		
		if(projection.isPresent() == false) {
			return false;
		}
		
		//-------------------------------------------------
		// Update Reservation		
		//-------------------------------------------------
		try {
			List<ReservationDTO> projectionReservations = this.reservationService.findProjectionReservations(projectionId);
			for (ReservationDTO reservation : projectionReservations) {
				reservation.setProjection(null);
				this.reservationService.updateDelete(reservation);
				this.reservationService.delete(reservation.getId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//-------------------------------------------------
		// Update Projection
		//-------------------------------------------------
		projection.get().setMovie(null);
		projection.get().setRoom(null);
		
		try {
			this.save(projection.get());
		} catch (ProjectionOverlapException e) {
			e.printStackTrace();
			return false;
		}
		//-------------------------------------------------
		
		this.projectionRepository.deleteById(projectionId);
		
		return true;
		
		
	}
	
	
	//---------------------------------------------------------------	
	// Custom ProjectionService Methods
	//---------------------------------------------------------------
	
	public List<ProjectionDTO> getMovieProjections(Long movieId) {
		
		
		Optional<MovieDTO> movie = this.movieRepository.findById(movieId);
		if(movie.isPresent() == false) {
			return null;
		}
		
		List<ProjectionDTO> movieProjections = this.projectionRepository.findByMovie(movie.get());
		
		return movieProjections;
	}
	
	
	public List<ProjectionDTO> getRoomsProjections(Long roomId) {
		
		// Get Room
		Optional<RoomDTO> room = this.roomRepository.findById(roomId);
		if(room.isPresent() == false) {
			return null;			// Room Not Found
		}
		
		
		List<ProjectionDTO> roomsProjections = this.projectionRepository.findByRoom(room.get());
		
		return roomsProjections;
	}
	
	
}
