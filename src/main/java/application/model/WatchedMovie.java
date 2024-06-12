package application.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "watched_movie")
public class WatchedMovie implements Serializable {

	private static final long serialVersionUID = 5752902961540736464L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDTO user;

	@ManyToOne
	@JoinColumn(name = "movie_id")
	private MovieDTO movie;

	@Column(name = "date")
	private Date date;

	public WatchedMovie() {

	}

	public WatchedMovie(UserDTO user, MovieDTO movie, Date date) {
		super();
		this.user = user;
		this.movie = movie;
		this.date = date;
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

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	
	public boolean equals(Object obj) {

		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof WatchedMovie)) {
			return false;
		}
		
		WatchedMovie watchedMovie = (WatchedMovie) obj;
		
		if(this.id == watchedMovie.getId()) {
			return true;
		}
		
		return false;
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.user, this.movie);
	}
	
	@Override
	public String toString() {
		return "WatchedMovie [id=" + id + ", user=" + user + ", movie=" + movie + ", date=" + date + "]";
	}

}
