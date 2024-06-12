package application.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import application.other.MovieGenre;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "movie")
public class MovieDTO implements Serializable {
	
	private static final long serialVersionUID = 2229150655740061324L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title", length = 25)
	private String title;
	
	@Column(name = "genre")
	private MovieGenre genre;
	
	@Column(name = "image", columnDefinition = "text", nullable = true)
//	@JsonProperty(access = Access.WRITE_ONLY) // Can use Password in HTTP Request but HTTP Response will not have password.
	@JsonIgnore
	private String image;
	
	@Column(name = "rating", nullable = true)
	private Float rating;
	
	@Column(name = "description", columnDefinition = "text")
	private String description;
	
	@Column(name = "authors")
	private List<String> authors;
	
	@Column(name = "actors")
	private List<String> actors;
	
	@Column(name = "year")
	private Integer year;
	
	@Column(name =  "country", length = 25)
	private String country;
	
	@Column(name =  "duration")
	private Integer duration;
	
	@Column(name = "trailer", columnDefinition = "text", nullable = true)
	private String trailer;
	
	
	public MovieDTO() {

	}

	public MovieDTO(String title, MovieGenre genre, String imagePath, Float rating, String description,  List<String> authors, List<String> actors, Integer year, String country, Integer duration, String trailer) {
		this.title = title;
		this.genre = genre;
		this.image = imagePath;
		this.rating = rating;
		this.description = description;
		this.authors = authors;
		this.actors = actors;
		this.year = year;
		this.country = country;
		this.duration = duration;
		this.trailer = trailer;
	}

	public MovieDTO(Long id, String title, MovieGenre genre, String image, Float rating, String description,  List<String> authors, List<String> actors, Integer year, String country, Integer duration, String trailer) {
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.image = image;
		this.rating = rating;
		this.description = description;
		this.authors = authors;
		this.actors = actors;
		this.year = year;
		this.country = country;
		this.duration = duration;
		this.trailer = trailer;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MovieGenre getGenre() {
		return genre;
	}

	public void setGenre(MovieGenre genre) {
		this.genre = genre;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean equals(MovieDTO movie) {

		if(this.id == movie.getId()) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", genre=" + genre + ", image=" + image + ", rating=" + rating + ", description=" + description + ", authors=" + authors + ", actors=" + actors + ", year=" + year + ", country=" + country + ", duration=" + duration + ", trailer=" + trailer + "]";
	}
	
}
