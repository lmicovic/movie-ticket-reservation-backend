package application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.model.RoomDTO;
import application.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController implements CRUDController<RoomDTO>{

	private RoomService roomeService;
	
	@Autowired
	public RoomController(RoomService roomeService) {
		this.roomeService = roomeService;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> getAll() {
		
		List<RoomDTO> rooms = this.roomeService.getAll();
		return new ResponseEntity<List<RoomDTO>>(rooms, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> getById(@PathVariable("roomId") Long id) {
		
		RoomDTO room = this.roomeService.getById(id);
		if(room == null) {
			return new ResponseEntity<String>("Room with id: " + id + " is not Found!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<RoomDTO>(room, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> save(@RequestBody RoomDTO object) {
		
		RoomDTO savedRoom = null;
		try {
			savedRoom = this.roomeService.save(object);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<RoomDTO>(savedRoom, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> update(@RequestBody RoomDTO object) {
		
		RoomDTO updatedRoom = null;
		try {
			updatedRoom = this.roomeService.update(object);
			
			if(updatedRoom == null) {
				return new ResponseEntity<String>("Room with id: " + object.getId() + " is not found!", HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<RoomDTO>(updatedRoom, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{roomId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> delete(@PathVariable("roomId") Long id) {
		
		this.roomeService.delete(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
}
