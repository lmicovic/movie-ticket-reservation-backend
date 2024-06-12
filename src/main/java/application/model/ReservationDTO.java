package application.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity(name = "reservation")
//@JsonDeserialize(using = ReservationDTODeserializer.class)
public class ReservationDTO implements Serializable {

	private static final long serialVersionUID = 2384209627011782144L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserDTO user;

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "projection_id", referencedColumnName = "id")
	private ProjectionDTO projection;

	@Column(name = "ticket_count")
	private Integer ticketCount;

	@Column(name = "total_price")
	private Float totalPrice;
	
	@Column(name = "reserved_seats")
	private Set<Integer> reservedSeats;
	
	@Column(name = "reservation_date")
	private Date reservationDate;
	
	public ReservationDTO() {

	}

	public ReservationDTO(UserDTO user, ProjectionDTO projection, Set<Integer> reservedSeats) {
		super();
		this.user = user;
		this.projection = projection;
		this.reservedSeats = reservedSeats;
		this.ticketCount = reservedSeats.size();
		this.totalPrice = this.projection.getPrice() * ticketCount.floatValue();
		
	}
	
	public ReservationDTO(UserDTO user, ProjectionDTO projection, Integer ticketCount, Float totalPrice, Set<Integer> reservedSeats, Date reservationDate) {
		super();
		this.user = user;
		this.projection = projection;
		this.ticketCount = ticketCount;
		this.totalPrice = totalPrice;
		this.reservedSeats = reservedSeats;
		this.reservationDate = reservationDate;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public ProjectionDTO getProjection() {
		return projection;
	}

	public void setProjection(ProjectionDTO projection) {
		this.projection = projection;
	}

	public Long getId() {
		return id;
	}

	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
	}

	public Integer getTicketCount() {
		return ticketCount;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setReservedSeats(Set<Integer> reservedSeats) {
		this.reservedSeats = reservedSeats;
	}
	
	public Set<Integer> getReservedSeats() {
		return reservedSeats;
	}
	
	public Date getReservationDate() {
		return reservationDate;
	}
	
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	
	public void reserveSeat(Integer seatNumber) throws Exception {
		
		if(seatNumber < 1 || seatNumber > (this.projection.getRoom().getColumns() * this.projection.getRoom().getRows())) {
			throw new Exception("Wrong seat number: " + seatNumber + " Seat number must be between 1 and " + (this.projection.getRoom().getColumns() * this.projection.getRoom().getRows()));
		}
		
		if(this.reservedSeats.contains(seatNumber)) {
			throw new Exception("Seat number: " + seatNumber + " is selected reserved.");
		}

		this.reservedSeats.add(seatNumber);
		
		this.ticketCount = this.reservedSeats.size();
		this.totalPrice = this.getProjection().getPrice() * this.ticketCount;
		
	}
	
	@Override
	public String toString() {
		return "ReservationDTO [id=" + id + ", ticketCount=" + ticketCount + ", totalPrice=" + totalPrice + ", reservedSeats=" + reservedSeats + ", reservationDate: " + reservationDate + "]";
	}
	
	

}
