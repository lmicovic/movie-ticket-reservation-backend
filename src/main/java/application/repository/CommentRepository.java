package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.model.CommentDTO;
import application.model.MovieDTO;
import application.model.UserDTO;

@Repository
public interface CommentRepository extends JpaRepository<CommentDTO, Long> {

	/**
	 * Find All Comment for specified Movie.
	 * 
	 * @param movie
	 * @return list of comments - for specified Movie.
	 */
	public List<CommentDTO> findByMovie(MovieDTO movie);
	
	
	/**
	 * Counts how many comments passed Movie have.
	 * 
	 * 
	 * @param movie
	 * @return count - number of Comments of Movie
	 */
	public Long countByMovie(MovieDTO movie);
	
	
	/**
	 * Find Users Comments
	 * 
	 * @param user
	 * @return users comments
	 */
	public List<CommentDTO> findByUser(UserDTO user);
	
	
}
