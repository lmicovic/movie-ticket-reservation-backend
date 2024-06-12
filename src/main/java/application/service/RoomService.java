package application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.ProjectionDTO;
import application.model.RoomDTO;
import application.repository.RoomRepository;

@Service
public class RoomService implements CRUDService<RoomDTO> {

	private RoomRepository roomRepository;
	private ProjectionService projectionService;
	
	@Autowired
	public RoomService(RoomRepository roomRepository, ProjectionService projectionService) {
		this.roomRepository = roomRepository;
		this.projectionService = projectionService;
	}

	@Override
	public List<RoomDTO> getAll() {
		
		List<RoomDTO> rooms = this.roomRepository.findAll();
		return rooms;
	}

	@Override
	public RoomDTO getById(Long id) {

		Optional<RoomDTO> room = this.roomRepository.findById(id);
		if(room.isPresent() == false) {
			return null;
		}
		
		return room.get();
	}

	@Override
	public RoomDTO save(RoomDTO object) throws Exception {
		
		RoomDTO savedRoom = this.roomRepository.save(object);
		return savedRoom;
	}

	@Override
	public RoomDTO update(RoomDTO object) throws Exception {
		
		if(this.roomRepository.existsById(object.getId()) == false) {
			return null;
		}
		
		RoomDTO updatedRoom = this.roomRepository.save(object);

		return updatedRoom;
	}

	@Override
	public boolean delete(Long roomId) {
		
		// Get All Rooms Projections
		List<ProjectionDTO> roomsProjections = this.projectionService.getRoomsProjections(roomId);
		
		for (ProjectionDTO projection : roomsProjections) {
			this.projectionService.delete(projection.getId());
		}
		
		this.roomRepository.deleteById(roomId);
		return true;
	}
	
	
	
}
