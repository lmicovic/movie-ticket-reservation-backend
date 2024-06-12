package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import application.configuration.jwt_security.UserInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity(name = "user_account")
//@JsonDeserialize(using = UserDTODeserializer.class)
public class UserDTO implements Serializable {

	private static final long serialVersionUID = -2778779664351068009L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "image", nullable = true)
	@JsonIgnore
	private String image;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
	@JoinColumn(name = "user_info_id", referencedColumnName = "id")
	private UserInfo userInfo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_bookmarks", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "movieId") })
	@JsonIgnore
	private List<MovieDTO> bookmarkedMovies = new ArrayList<MovieDTO>();
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<WatchedMovie> watchedMovies = new ArrayList<WatchedMovie>();
	
	public UserDTO() {

	}
	
	public UserDTO(String firstname, String lastname, String image,  String email, String password) {
		
		UserInfo userInfo = new UserInfo();
		
		userInfo.setFirstname(firstname);
		userInfo.setLastname(lastname);
		userInfo.setEmail(email);
		userInfo.setPassword(password);
		userInfo.setRoles("ROLE_USER");
		
		this.image = image;
		this.userInfo = userInfo;
		
	}
	
	public UserDTO(Long id, String firstname, String lastname, String email, String password) {

		this.id = id;
		
		UserInfo userInfo = new UserInfo();
		
		userInfo.setFirstname(firstname);
		userInfo.setLastname(lastname);
		userInfo.setEmail(email);
		userInfo.setPassword(password);
		userInfo.setRoles("ROLE_USER");
		
		this.userInfo = userInfo;
	}
	
	public UserDTO(UserInfo userInfo, String image) {		
		
		this.image = image;
		this.userInfo = userInfo;
		
	}
	
	public UserDTO(Long id, UserInfo userInfo) {

		this.id = id;
		this.userInfo = userInfo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<MovieDTO> getBookmarkedMovies() {
		return bookmarkedMovies;
	}
	
	public void setBookmarkedMovies(List<MovieDTO> bookmarkedMovies) {
		this.bookmarkedMovies = bookmarkedMovies;
	} 
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public void addToBookmarks(MovieDTO movie) throws Exception {
		
		if(this.bookmarkedMovies.contains(movie)) {
			throw new Exception("Movie: " + movie.getTitle() + " is already bookmarked by User.");
		}
		
		this.bookmarkedMovies.add(movie);
	}
	
	public boolean removeFromBookmarks(MovieDTO movie) throws Exception {
		
		// Check if Movie is in User Bookmarks
		if(this.bookmarkedMovies.contains(movie) == false) {
			throw new Exception("Movie with Id: " + movie.getId() + " is not in User Id: " + this.id + " bookmarks!");
		}
		
		return this.bookmarkedMovies.remove(movie);
		
	}
	
	public List<WatchedMovie> getWatchedMovies() {
		return watchedMovies;
	}
	
	public void setWatchedMovies(List<WatchedMovie> watchedMovies) {
		this.watchedMovies = watchedMovies;
	}
	
	public void addWatchedMovie(MovieDTO movie) {
		
		WatchedMovie watchedMovie = new WatchedMovie(this, movie, new Date());
		this.watchedMovies.add(watchedMovie);
	}
	
	@Override
	public String toString() {
		return "UserDTO [id=" + id + " " + this.userInfo + "]";
	}
		
}
