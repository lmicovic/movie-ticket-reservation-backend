package application.model;

import java.io.Serializable;
import java.sql.Time;
import application.configuration.Util;
import application.configuration.converter.ProjectionDateConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity(name = "projection")
public class ProjectionDTO implements Serializable {

	private static final long serialVersionUID = 9190947332245614029L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "movie_id")
	private MovieDTO movie;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "room_id")
	private RoomDTO room;

	@Column(name = "projection_date", columnDefinition = "text")
	@Embedded
	@Convert(converter = ProjectionDateConverter.class, attributeName = "date")
	private ProjectionDate date;
	
	/**
	 * Price per Ticket
	 */
	@Column(name = "price")
	private Float price;
	
	public ProjectionDTO() {

	}

	public ProjectionDTO(MovieDTO movie, RoomDTO room, ProjectionDate date, Float price) {
		super();
		this.movie = movie;
		this.room = room;
		this.date = date;
		this.price = price;
	}

	public MovieDTO getMovie() {
		return movie;
	}

	public void setMovie(MovieDTO movie) {
		this.movie = movie;
	}

	public RoomDTO getRoom() {
		return room;
	}

	public void setRoom(RoomDTO room) {
		this.room = room;
	}

	public ProjectionDate getProjectionDate() {
		return date;
	}

	public void setProjectionDate(ProjectionDate date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}
	
	public Float getPrice() {
		return price;
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
	
	@SuppressWarnings("deprecation")
	public boolean projectionDateOverlap(ProjectionDTO projecton) {
		
		try {
			
//			System.out.println("\n\n\n----------------------------------");
//			System.out.println("Projection 1");
//			System.out.println("----------------------------------");
//			System.out.println("Room: " + this.getRoom().getName());
//			System.out.println("Day: " + Util.getDayNumber(this.getProjectionDate().getDay()));
//			System.out.println("Time: " + this.getProjectionDate().getHours() + ":" + this.getProjectionDate().getMinutes());
//			System.out.println("----------------------------------\n\n\n");
//			
//			System.out.println("----------------------------------");
//			System.out.println("Projection 2");
//			System.out.println("----------------------------------");
//			System.out.println("Room: " + projecton.getRoom().getName());
//			System.out.println("Day: " + Util.getDayNumber(projecton.getProjectionDate().getDay()));
//			System.out.println("Time: " + projecton.getProjectionDate().getHours() + ":" + projecton.getProjectionDate().getMinutes());
//			System.out.println("----------------------------------\n\n\n");
			
			RoomDTO thisRoom = this.getRoom();
			RoomDTO thatRoom = projecton.getRoom();
			
			// If Rooms are same there is no possible Overlap
			if(thisRoom.equals(thatRoom) == false) {
				return false;
			}
			
			// If Rooms are same there is possibility for Overlap
			if(thisRoom.equals(thatRoom)) {
				
				int thisDay = Util.getDayNumber(this.getProjectionDate().getDay());
				int thatDay = Util.getDayNumber(projecton.getProjectionDate().getDay());
				
				// If Days are not same then there is no Overlapping
				if(thisDay != thatDay) {
					return false;
				}
				
				// If Day are same then there are possible Overlapping
				if(thisDay == thatDay) {
					
					Time thisStartTime = new Time(this.getProjectionDate().getHours(), this.getProjectionDate().getMinutes(), 0);
					Time thisEndTime = new Time(this.getProjectionDate().getHours(), this.getProjectionDate().getMinutes() + this.getMovie().getDuration(), 0);
					
					Time thatStartTime = new Time(projecton.getProjectionDate().getHours(), projecton.getProjectionDate().getMinutes(), 0);
					Time thatEndTime = new Time(projecton.getProjectionDate().getHours(), projecton.getProjectionDate().getMinutes() + projecton.getMovie().getDuration(), 0);
					
					boolean overlap = (Math.min(thisEndTime.getTime(), thatEndTime.getTime()) - Math.max(thisStartTime.getTime(), thatStartTime.getTime())) > 0; 
					return overlap;
				}
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	@Override
	public String toString() {
		return "ProjectionDTO [id=" + id + ", movie=" + movie + ", room=" + room + ", date=" + date + ", price=" + price + "]";
	}
	
	

}
