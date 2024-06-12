package application.configuration;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import application.configuration.jwt_security.UserInfo;
import application.configuration.jwt_security.UserInfoService;
import application.model.MovieDTO;
import application.model.ProjectionDTO;
import application.model.ProjectionDate;
import application.model.ReservationDTO;
import application.model.RoomDTO;
import application.model.UserDTO;
import application.other.Day;
import application.other.MovieGenre;
import application.repository.MovieRepository;
import application.repository.ProjectionRepository;
import application.repository.ReservationRepository;
import application.repository.RoomRepository;
import application.repository.UserRepository;


@Component
public class ApplicationRunner implements CommandLineRunner {
	
	private UserRepository userRepository;
	private UserInfoService userInfoService;
	private MovieRepository movieRepository;
	private RoomRepository roomRepository;
	private ProjectionRepository projectionRepository;
	private ReservationRepository reservationRepository;
//	private CommentRepository commentRepository;

	
	
	@Autowired
	public ApplicationRunner(UserRepository userRepository, UserInfoService userInfoService, MovieRepository movieRepository, RoomRepository roomRepository, ProjectionRepository projectionRepository, ReservationRepository reservationRepository) {
		this.userRepository = userRepository;
		this.userInfoService = userInfoService;
		this.movieRepository = movieRepository;
		this.roomRepository = roomRepository;
		this.projectionRepository = projectionRepository;
		this.reservationRepository = reservationRepository;
	}
	
	public void run(String... args) throws Exception {

//		System.err.println(this.enableSpringSecurity);
		
		this.initUsers();
		this.initMovies();
		this.initRoom();
		this.initComments();
		
	}
	
	private void initUsers() {
		
		File userImageFolder = new File("./src/main/resources/images/users");
		
		// Create new Users
		UserInfo userInfo1 = new UserInfo("Pera", "Peric", "peraperic@gmail.com", "Test!123!", "ROLE_USER");
		UserInfo userInfo2 = new UserInfo("Pera", "Anic", "peraanic@gmail.com", "Test!123!", "ROLE_USER");
		UserInfo userInfo3 = new UserInfo("Ana", "Peric", "anaperic@gmail.com", "Test!123!", "ROLE_USER");
		
		try {
			userInfo1 = this.userInfoService.addUser(userInfo1);
			userInfo2 = this.userInfoService.addUser(userInfo2);
			userInfo3 = this.userInfoService.addUser(userInfo3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		UserDTO user1 = new UserDTO(userInfo1, userImageFolder.getPath() + "\\user-profil-image-empty.png");
		UserDTO user2 = new UserDTO(userInfo2, userImageFolder.getPath() + "\\user-profil-image-empty.png");
		UserDTO user3 = new UserDTO(userInfo3, null);
		
		user1 = this.userRepository.save(user1);
		user2 = this.userRepository.save(user2);
		user3 = this.userRepository.save(user3);

		
		
//		this.userRepository.save(user1);
//		this.userRepository.save(user2);
//		this.userRepository.save(user3);
		
		
		
	}
	
	private void initMovies() {
		
		File movieImageFolder = new File("./src/main/resources/images/movie");
		
		String movieImageName = "\\background.png";
//		String movieImageName = "image-placeholder.png";
//		String movieImageName = "image-placeholder-1.jpg";
		
		MovieDTO movie1 = new MovieDTO("Title1", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		MovieDTO movie2 = new MovieDTO("Title2", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		MovieDTO movie3 = new MovieDTO("Title3", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		MovieDTO movie4 = new MovieDTO("Title4", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		MovieDTO movie5 = new MovieDTO("Title5", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		MovieDTO movie6 = new MovieDTO("Title6", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		
		
		this.movieRepository.save(movie1);
		this.movieRepository.save(movie2);
		this.movieRepository.save(movie3);
		this.movieRepository.save(movie4);
		this.movieRepository.save(movie5);
		this.movieRepository.save(movie6);
		
		
		Optional<UserDTO> user1 = this.userRepository.findById(1l);
		Optional<UserDTO> user2 = this.userRepository.findById(2l);
		
		// User Bookmarked Movies
		try {
			user1.get().addToBookmarks(movie1);
			user1.get().addToBookmarks(movie2);
			user2.get().addToBookmarks(movie2);
			user2.get().addToBookmarks(movie3);
			
			this.userRepository.save(user1.get());
			this.userRepository.save(user2.get());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		user1.get().addWatchedMovie(movie1);
		user1.get().addWatchedMovie(movie2);
		user2.get().addWatchedMovie(movie2);
		user2.get().addWatchedMovie(movie1);
		
		this.userRepository.save(user1.get());
		this.userRepository.save(user2.get());
		
		
		
	}
	
	private void initRoom() {
		
		RoomDTO room1 = new RoomDTO("Room 1", 10, 8);
		RoomDTO room2 = new RoomDTO("Room 2", 12, 5);
		RoomDTO room3 = new RoomDTO("Room 3", 14, 6);
		RoomDTO room4 = new RoomDTO("Room 4", 12, 5);
		RoomDTO room5 = new RoomDTO("Room 5", 14, 16);
		
		this.roomRepository.save(room1);
		this.roomRepository.save(room2);
		this.roomRepository.save(room3);
		this.roomRepository.save(room4);
		this.roomRepository.save(room5);
		
		Optional<MovieDTO> movie1 = this.movieRepository.findById(1l);
//		Optional<MovieDTO> movie2 = this.movieRepository.findById(1l);
//		Optional<MovieDTO> movie3 = this.movieRepository.findById(1l);
		
		
		ProjectionDTO projection1 = new ProjectionDTO(movie1.get(), room5, new ProjectionDate(Day.Monday, 11, 30), 10.25f);
		ProjectionDTO projection2 = new ProjectionDTO(movie1.get(), room5, new ProjectionDate(Day.Monday, 11, 00), 15.0f);
		ProjectionDTO projection3 = new ProjectionDTO(movie1.get(), room3, new ProjectionDate(Day.Monday, 18, 30), 20.5f);
		
		ProjectionDTO projection4 = new ProjectionDTO(movie1.get(), room3, new ProjectionDate(Day.Wednesday, 11, 00), 20.5f);
		ProjectionDTO projection5 = new ProjectionDTO(movie1.get(), room1, new ProjectionDate(Day.Wednesday, 12, 00), 20.5f);
		ProjectionDTO projection6 = new ProjectionDTO(movie1.get(), room3, new ProjectionDate(Day.Wednesday, 20, 00), 20.5f);
		
		ProjectionDTO projection7 = new ProjectionDTO(movie1.get(), room1, new ProjectionDate(Day.Thursday, 9, 00), 20.5f);
		ProjectionDTO projection8 = new ProjectionDTO(movie1.get(), room4, new ProjectionDate(Day.Thursday, 11, 00), 20.5f);
		ProjectionDTO projection9 = new ProjectionDTO(movie1.get(), room5, new ProjectionDate(Day.Thursday, 15, 00), 20.5f);
		
		ProjectionDTO projection10 = new ProjectionDTO(movie1.get(), room1, new ProjectionDate(Day.Friday, 12, 00), 20.5f);
		ProjectionDTO projection11 = new ProjectionDTO(movie1.get(), room2, new ProjectionDate(Day.Friday, 13, 30), 20.5f);
		ProjectionDTO projection12 = new ProjectionDTO(movie1.get(), room4, new ProjectionDate(Day.Friday, 16, 30), 20.5f);
		
		ProjectionDTO projection13 = new ProjectionDTO(movie1.get(), room1, new ProjectionDate(Day.Saturday, 14, 00), 20.5f);
		ProjectionDTO projection14 = new ProjectionDTO(movie1.get(), room2, new ProjectionDate(Day.Saturday, 14, 30), 20.5f);
		ProjectionDTO projection15 = new ProjectionDTO(movie1.get(), room1, new ProjectionDate(Day.Saturday, 22, 00), 20.5f);
		
		
		
		this.projectionRepository.save(projection1);
		this.projectionRepository.save(projection2);
		this.projectionRepository.save(projection3);
		
		this.projectionRepository.save(projection4);
		this.projectionRepository.save(projection5);
		this.projectionRepository.save(projection6);
		
		this.projectionRepository.save(projection7);
		this.projectionRepository.save(projection8);
		this.projectionRepository.save(projection9);
		
		this.projectionRepository.save(projection10);
		this.projectionRepository.save(projection11);
		this.projectionRepository.save(projection12);
		
		this.projectionRepository.save(projection13);
		this.projectionRepository.save(projection14);
		this.projectionRepository.save(projection15);
		
		
		Optional<UserDTO> user = this.userRepository.findById(1l);
		Optional<UserDTO> user2 = this.userRepository.findById(2l);
		
		ReservationDTO reservation = new ReservationDTO(user.get(), projection1, new HashSet<Integer>());
		ReservationDTO reservation2 = new ReservationDTO(user2.get(), projection1, new HashSet<Integer>());
		ReservationDTO reservation3 = new ReservationDTO(user2.get(), projection1, new HashSet<Integer>());
		
		reservation = this.reservationRepository.save(reservation);
		reservation2 =  this.reservationRepository.save(reservation2);
		
		try {
			reservation.reserveSeat(1);
			reservation.reserveSeat(2);
			reservation.reserveSeat(3);
			reservation.reserveSeat(4);
			
			reservation2.reserveSeat(10);
			reservation2.reserveSeat(11);
			reservation2.reserveSeat(12);
			
			reservation3.reserveSeat(15);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		reservation = this.reservationRepository.save(reservation);
		reservation2 = this.reservationRepository.save(reservation2);
		reservation3 = this.reservationRepository.save(reservation3);
		

		try {
			reservation.reserveSeat(100);
			reservation = this.reservationRepository.save(reservation);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
	}
	
	private void initComments() {
		
//		Optional<UserDTO> user1 = this.userRepository.findById(1l);
//		Optional<UserDTO> user2 = this.userRepository.findById(2l);
//		Optional<MovieDTO> movie1 = this.movieRepository.findById(1l);
		
//		CommentDTO comment1 = new CommentDTO(user1.get(), movie1.get(), new Date(), 3, "Varius duis at consectetur lorem donec massa sapien faucibus et molestie ac feugiat sed lectus vestibulum mattis ullamcorper velit sed ullamcorper morbi tincidunt ornare massa eget egestas purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae auctor eu augue ut lectus arcu bibendum at varius vel pharetra vel turpis.");
//		CommentDTO comment2 = new CommentDTO(user2.get(), movie1.get(), new Date(), 4, "Interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam.");
		
//		this.commentRepository.save(comment1);
//		this.commentRepository.save(comment2);
		
		
		
		
		
	}
	
	
	
}

