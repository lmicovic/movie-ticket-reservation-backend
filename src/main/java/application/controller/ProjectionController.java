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
import application.configuration.exceptions.ProjectionOverlapException;
import application.model.ProjectionDTO;
import application.service.ProjectionService;

@Controller
@RequestMapping(value = "/projection")
public class ProjectionController implements CRUDController<ProjectionDTO> {

	private ProjectionService projectionService;

	@Autowired
	public ProjectionController(ProjectionService projectionService) {
		this.projectionService = projectionService;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
		
		List<ProjectionDTO> projections = this.projectionService.getAll();
		return new ResponseEntity<List<ProjectionDTO>>(projections, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {

		ProjectionDTO projection = this.projectionService.getById(id);
		if(projection == null) {
			return new ResponseEntity<String>("Projection with id: " + id + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ProjectionDTO>(projection, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody ProjectionDTO projection) {
		
		try {
		
			
			// Current Projection is Overlapping Time and Room with other Projection
			ProjectionDTO savedProjection = this.projectionService.save(projection);
			
			return new ResponseEntity<ProjectionDTO>(savedProjection, HttpStatus.OK);
			
		} catch (ProjectionOverlapException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		
		
		
		
		
		
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody ProjectionDTO projection) {
		
		@SuppressWarnings("deprecation")
		ProjectionDTO updatedProjection = this.projectionService.update(projection);
		if(updatedProjection == null) {
			return new ResponseEntity<String>("Projection with id: " + projection.getId() + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ProjectionDTO>(updatedProjection, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectionId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("projectionId") Long projectionId) {
		
		boolean deleted = this.projectionService.delete(projectionId);
		if(deleted == false) {
			return new ResponseEntity<String>("Projection with id: " + projectionId + " is not found.", HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	
	//---------------------------------------------------------------	
	// Custom ProjectionService Methods
	//---------------------------------------------------------------
	
	@RequestMapping(value = "/movie/{movieId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMovieProjections(@PathVariable("movieId") Long movieId) {
		
		List<ProjectionDTO> movieProjections = this.projectionService.getMovieProjections(movieId);
		if(movieProjections == null) {
			return new ResponseEntity<String>("Movie with id:" + movieId + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<ProjectionDTO>>(movieProjections, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/room/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRoomsProjections(@PathVariable("roomId") Long roomId) {
		
		List<ProjectionDTO> roomsProjections = this.projectionService.getRoomsProjections(roomId);
		
		return new ResponseEntity<List<ProjectionDTO>>(roomsProjections, HttpStatus.OK);
	}
	
}
