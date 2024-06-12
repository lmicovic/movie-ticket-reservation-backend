package application.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "comment")
//@JsonDeserialize(using = CommentDTODeserializer.class)
public class CommentDTO implements Serializable {

	private static final long serialVersionUID = 6855198274217429999L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserDTO user;

	@ManyToOne
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	private MovieDTO movie;

	@Column(name = "date")
	private Date date;

	@Column(name = "comment_rating")
	private Integer rating;

	@Column(name = "text", columnDefinition = "text")
	private String commentText;
	
	public CommentDTO() {

	}
	
	public CommentDTO(UserDTO user, MovieDTO movie, Date date, Integer rating, String commentText) {
		super();
		this.user = user;
		this.movie = movie;
		this.date = date;
		this.rating = rating;
		this.commentText = commentText;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public MovieDTO getMovie() {
		return movie;
	}

	public void setMovie(MovieDTO movie) {
		this.movie = movie;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "CommentDTO [id=" + id + ", user=" + user.getId() + ", movie=" + movie.getId() + ", date=" + date + ", rating=" + rating + ", commentText=" + commentText + "]";
	}

}
