package application.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import application.model.ProjectionDTO;
import application.model.ReservationDTO;
import application.model.UserDTO;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationDTO, Long> {
	
	/**
	 * Get All User Reservations.
	 * 
	 * @param user
	 * @return users reservation
	 */
	public List<ReservationDTO> findByUser(UserDTO user);
	
	
	/**
	 * Get All Reservation for some Projection.
	 * 
	 * @param projection
	 * @return reservations - all projection reservation
	 */
	public List<ReservationDTO> findByProjection(ProjectionDTO projection);
	
	
	
}
