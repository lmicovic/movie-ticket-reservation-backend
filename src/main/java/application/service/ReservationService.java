package application.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.ProjectionDTO;
import application.model.ReservationDTO;
import application.model.RoomDTO;
import application.model.UserDTO;
import application.repository.ProjectionRepository;
import application.repository.ReservationRepository;
import application.repository.RoomRepository;
import application.repository.UserRepository;

@Service
public class ReservationService implements CRUDService<ReservationDTO> {

	private ReservationRepository reservationRepository;
	private UserRepository userRepository;
	private ProjectionRepository projectionRepository;
	private RoomRepository roomRepository;
	
	@Autowired
	public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, ProjectionRepository projectionRepository, RoomRepository roomRepository) {
		this.reservationRepository = reservationRepository;
		this.userRepository = userRepository;
		this.projectionRepository = projectionRepository;
		this.roomRepository = roomRepository;
	}

	public List<ReservationDTO> getAll() {
		
		List<ReservationDTO> reservations = this.reservationRepository.findAll();
		return reservations;
	}

	public ReservationDTO getById(Long id) {

		Optional<ReservationDTO> reservation = this.reservationRepository.findById(id);
		if(reservation.isPresent() == false) {
			return null;
		}
		
		return reservation.get();
	}

	public ReservationDTO save(ReservationDTO object) throws Exception {
		
		// Projection Check
		ProjectionDTO projection = object.getProjection();
		if(projection == null) {
			throw new Exception("Reservation: " + object.getId() + " projection is null.");
		}
		
		// Get All Projection Reservations and Check Seats
		List<ReservationDTO> projectionReservations = this.reservationRepository.findByProjection(projection);
		if(projectionReservations.isEmpty() || projectionReservations == null) {
			projectionReservations = new ArrayList<ReservationDTO>();
		}
		
		// Here are now all reserved seats
		Set<Integer> totalReservedSeats = new HashSet<Integer>();
		for (ReservationDTO projectionReservation : projectionReservations) {
			totalReservedSeats.addAll(projectionReservation.getReservedSeats());
		}
		
//		System.out.println("Reserved Seats: " + totalReservedSeats);
//		System.out.println("Reserve Seats: " + object.getReservedSeats());
		
		// Check if out new Reserveation selected Seats are Reserved
		for (Integer seat : object.getReservedSeats()) {
			if(totalReservedSeats.contains(seat)) {
				throw new Exception("Seat with number: " + seat + " is reserved.");
			}
			if(seat < 1 || seat > (object.getProjection().getRoom().getColumns() * object.getProjection().getRoom().getRows())) {
				throw new Exception("Wrong Seat number: " + seat + " seat number must be between 1 and " + (object.getProjection().getRoom().getColumns() * object.getProjection().getRoom().getRows()));
			}
		}
		
		object.setTicketCount(object.getReservedSeats().size());
		object.setTotalPrice(object.getProjection().getPrice() * object.getTicketCount());
		
		// If All seats passed check - save ReservationDTO
		ReservationDTO savedReservation = this.reservationRepository.save(object);
		
		return savedReservation;
	}
	
	/**
	 * This is only used in Reservation Delete Method
	 */
	@Deprecated
	public ReservationDTO updateDelete(ReservationDTO reservation) {
		reservation = this.reservationRepository.save(reservation);
		return reservation;
	}
	
	public ReservationDTO update(ReservationDTO object) throws Exception {

		Optional<ReservationDTO> reservation = this.reservationRepository.findById(object.getId());
		if(reservation.isPresent() == false) {
			return null;
		}
		
		ReservationDTO updatedReservation = this.save(object);
		
		return updatedReservation;
	}

	public boolean delete(Long id) {

		if(this.reservationRepository.existsById(id) == false) {
			return false;
		}
		
		this.reservationRepository.deleteById(id);
		
		return true;
	}
	
	
	
	//---------------------------------------------------------------	
	// Custom ReservationService Methods
	//---------------------------------------------------------------
	public List<ReservationDTO> getUserReservations(Long userId) {
		
		Optional<UserDTO> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			return null;
		}
		
		List<ReservationDTO> userReservations = this.reservationRepository.findByUser(user.get());
		return userReservations;
		
	}
	
	public List<ReservationDTO> findProjectionReservations(Long projectionId) throws Exception {
		
		Optional<ProjectionDTO> projection = this.projectionRepository.findById(projectionId);
		if(projection.isPresent() == false) {
			throw new Exception("Projection with id: " + projectionId + " is not found");
		}
		
		List<ReservationDTO> projectonReservationsList = this.reservationRepository.findByProjection(projection.get());
		
		return projectonReservationsList;
		
		
	}
	
	/**
	 * Gets All Reserved Seats for Projection
	 */
	public List<Integer> getProjectionReservationSeats(Long projectionId) {
		
		Optional<ProjectionDTO> projection = this.projectionRepository.findById(projectionId);
		
		// If Projection is not found
		if(projection.isPresent() == false) {
			return null;
		}
		
		// Get All Reservations for Projection
		List<ReservationDTO> projectionReservations = this.reservationRepository.findByProjection(projection.get());
		List<Integer> projectionReservationSeats = new ArrayList<Integer>();
		
		for (ReservationDTO reservation : projectionReservations) {
			projectionReservationSeats.addAll(reservation.getReservedSeats());
		}
		
		return projectionReservationSeats;
		
	}
	
	
	public List<ReservationDTO> getRoomReservations(Long roomId) {
		
		Optional<RoomDTO> room = this.roomRepository.findById(roomId);
		if(room.isPresent() == false) {
			return null;
		}
		
		List<ProjectionDTO> roomProjections = this.projectionRepository.findByRoom(room.get());
		List<ReservationDTO> roomsReservations = new ArrayList<ReservationDTO>();
		for (ProjectionDTO projection : roomProjections) {
			
			try {
				
				List<ReservationDTO> projectionReservations = this.findProjectionReservations(projection.getId());				
				roomsReservations.addAll(projectionReservations);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return roomsReservations;			
	}
	
}
