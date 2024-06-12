package application.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import application.model.MovieDTO;
import application.model.ProjectionDTO;
import application.model.RoomDTO;
import application.other.Day;

public interface ProjectionRepository extends JpaRepository<ProjectionDTO, Long> {

	/**
	 * Find Projection by provided Room and Projection Day.
	 * 
	 * @param room
	 * @param day
	 * @return projections - list of projections with same Room and Day.
	 */
	public List<ProjectionDTO> findByRoomAndDate_Day(RoomDTO room, Day day);
	
	public List<ProjectionDTO> findByMovie(MovieDTO movie);
	
	public List<ProjectionDTO> findByRoom(RoomDTO room);
	
}
