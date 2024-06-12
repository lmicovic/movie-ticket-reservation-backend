package application.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import application.model.ReservationDTO;
import application.service.ReservationService;

@Controller
@RequestMapping(value = "/reservation")
public class ReservationController implements CRUDController<ReservationDTO> {

	private ReservationService reservationService;

	@Autowired
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
		
		List<ReservationDTO> reservations = this.reservationService.getAll();
		
		return new ResponseEntity<List<ReservationDTO>>(reservations,HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable("userId") Long id) {
		
		ReservationDTO reservation = this.reservationService.getById(id);
		if(reservation == null) {
			return new ResponseEntity<String>("No Reservation with id: " + id, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ReservationDTO>(reservation, HttpStatus.OK);
		
	}

	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody ReservationDTO object) {
		
		System.out.println(object);
		
		ReservationDTO savedReservation;
		try {
			savedReservation = this.reservationService.save(object);
			
			return new ResponseEntity<ReservationDTO>(savedReservation, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		
	}

	
	@RequestMapping(method = RequestMethod.PUT, consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody ReservationDTO object) {

		
		try {
		
			ReservationDTO updatedReservation = this.reservationService.update(object);
			if(updatedReservation == null) {
				return new ResponseEntity<String>("No Reservation with id: " + object.getId(), HttpStatus.NOT_FOUND); 
			}
			
			return new ResponseEntity<ReservationDTO>(updatedReservation, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		
	}

	@RequestMapping(value = "/{reservationId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("reservationId") Long id) {
		
		boolean deleted = this.reservationService.delete(id);
		
		if(deleted == false) {
			return new ResponseEntity<String>("No Reservation with id: " + id, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	
	//---------------------------------------------------------------	
	// Custom ReservationController Methods
	//---------------------------------------------------------------
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserReservations(@PathVariable("userId") Long userId) {
		
		List<ReservationDTO> userReservations = this.reservationService.getUserReservations(userId);
		if(userReservations == null) {
			return new ResponseEntity<String>("User with id: " + userId + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<ReservationDTO>>(userReservations, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/projection/{projectionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findProjectionReservations(@PathVariable("projectionId") Long projectionId) {
		
		try {
			
			List<ReservationDTO> projectionReservations = this.reservationService.findProjectionReservations(projectionId);
			
			return new ResponseEntity<List<ReservationDTO>>(projectionReservations, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@RequestMapping(value = "/projection/reserved-seats/{projectionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getProjectionReservedSeats(@PathVariable("projectionId") Long projectionId) {
		
		List<Integer> projectionReservedSeats = this.reservationService.getProjectionReservationSeats(projectionId);
		if(projectionReservedSeats == null) {
			return new ResponseEntity<String>("Projection with id: " + projectionId + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Integer>>(projectionReservedSeats, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/room/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRoomsReservatons(@PathVariable("roomId") Long roomId) {
		
		List<ReservationDTO> roomReservations = this.reservationService.getRoomReservations(roomId);
		
		return new ResponseEntity<List<ReservationDTO>>(roomReservations, HttpStatus.OK);
		
	}
	
	
}
