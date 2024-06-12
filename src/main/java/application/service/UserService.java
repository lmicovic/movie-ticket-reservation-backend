package application.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import application.model.MovieDTO;
import application.model.UserDTO;
import application.model.WatchedMovie;
import application.repository.MovieRepository;
import application.repository.UserRepository;

@Service
public class UserService implements CRUDService<UserDTO> {

	private UserRepository userRepository;
	private MovieRepository movieRepository;
	
	@Autowired
	public UserService(UserRepository userRepository, MovieRepository movieRepository) {
		this.userRepository = userRepository;
		this.movieRepository = movieRepository;
	}

	public List<UserDTO> getAll() {
		
		List<UserDTO> users = this.userRepository.findAll();
		
		return users;
	}

	public UserDTO getById(Long id) {

		Optional<UserDTO> userOption = this.userRepository.findById(id);
		if(userOption.isPresent()) {
			return userOption.get();
		}
		
		return null;
	}

	public UserDTO save(UserDTO object) {
		
		UserDTO savedUser = this.userRepository.save(object);
		
		return savedUser;
	}

	public UserDTO update(UserDTO object) {
		
//		System.out.println(object);
		
		// Exception if password is null
		if(object.getUserInfo().getPassword() == null) {
			object.getUserInfo().setPassword(this.userRepository.findById(object.getId()).get().getUserInfo().getPassword());
		}
		
		if(object.getImage() == null) {
			object.setImage(this.userRepository.getUserImagePathById(object.getId()));
		}
		
		Optional<UserDTO> findUser = this.userRepository.findById(object.getId());
		if(findUser.isPresent()) {
			object = this.userRepository.save(object);
			return object;
		}
		
		return null;
	}

	public boolean delete(Long id) {
		
		Optional<UserDTO> findUser = this.userRepository.findById(id);
		if(findUser.isPresent()) {
			this.userRepository.deleteById(id);
			return true;
		}
		
		
		return false;
		
	}
	
	
	
	//---------------------------------------------------------------	
	// Custom UserService Methods
	//---------------------------------------------------------------
	public byte[] getUserImage(Long id) throws Exception {
		
		if(this.userRepository.existsById(id) == false) {
			throw new Exception("User with id: " + id + " does not exists.");
		}
		
		String userImagePath = this.userRepository.getUserImagePathById(id);
		if(userImagePath == null || userImagePath.equals(null) || userImagePath.equals("") || userImagePath.length() == 0) {
			return null;
		}
		
		File userImageFile = new File(userImagePath);
		
		if(userImageFile.exists() == false) {
			throw new Exception("Image file: " + userImageFile.getPath() + " for User: " + id + " does not exist." );
		}
		
		return Files.readAllBytes(Paths.get(userImageFile.getAbsolutePath()));
		
	}
	
	public UserDTO saveUserImage(Long userId, MultipartFile file) throws Exception {
		
		//------------------------------------------------------------
		// File Extension Check
		//------------------------------------------------------------
		List<String> allowedExtensions = Arrays.asList(".png", ".jpg");
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."), file.getOriginalFilename().length());
		
		// If not allowed extension
		if(allowedExtensions.contains(extension) == false) {
			throw new Exception("Unsupporeted File extension: " + extension);
		}
		//------------------------------------------------------------
		// File Extension Check - END
		//------------------------------------------------------------

		
		//------------------------------------------------------------
		// Save Image File to File System
		//------------------------------------------------------------
		File imageFile = new File("./src/main/resources/images/users/" + file.getOriginalFilename());
		try {
			
			if(imageFile.exists()) {

				// Rename File
//				int count = 1;
//				while(imageFile.exists() == true) {
//					imageFile = new File("./src/main/resources/images/users/" + file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 4) + "-" + count + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4, file.getOriginalFilename().length()));
//					count++;
//				}
				
				imageFile.delete();
				
			}

			imageFile.createNewFile();

			FileOutputStream fos = new FileOutputStream(imageFile);
			fos.write(file.getBytes());
			fos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//------------------------------------------------------------
		// Save Image File to File System - END
		//------------------------------------------------------------
		
		//------------------------------------------------------------
		// Add Image to User
		//------------------------------------------------------------		
		Optional<UserDTO> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			throw new Exception("User with id: " + userId + " is not found.");
		}
		
		user.get().setImage(imageFile.getPath());
		
		
		//------------------------------------------------------------
		// Update User in Database
		//------------------------------------------------------------
		
		this.userRepository.save(user.get());
		
		return user.get();
	}
	
	
	
	public List<MovieDTO> getUserBookmarks(Long userId) {
		
		Optional<UserDTO> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			return null;
		}
		
		return user.get().getBookmarkedMovies();
		
	}
	
	public UserDTO addMovieToUserBookmarks(Long userId, Long movieId) throws Exception {
		
		Optional<UserDTO> user = this.userRepository.findById(userId);
		Optional<MovieDTO> movie = this.movieRepository.findById(movieId);
		
		// User - NOT_FOUUND
		if(user.isPresent() == false) {
			throw new Exception("User with id: " + userId + " is not found.");
		}
		
		// Movie - NOT_FOUND
		if(movie.isPresent() == false) {
			throw new Exception("Movie with id: " + movieId + " is not found.");
		}
			
		user.get().addToBookmarks(movie.get());		// If movie is already in Users bookmark throws Exception().
		
		UserDTO updatedUser = this.update(user.get());
		
		return updatedUser;
	}
	
	public List<MovieDTO> removeMovieToUserBookmarks(Long userId, Long movieId) throws Exception {
		
		//----------------------------------------------------------------------------------
		// Get User by ID
		//----------------------------------------------------------------------------------
		Optional<UserDTO> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			throw new Exception("User with id: " + userId + " is not found.");
		}
		//----------------------------------------------------------------------------------
		
		//----------------------------------------------------------------------------------
		// Get MovieBy ID
		//----------------------------------------------------------------------------------
		Optional<MovieDTO> movie = this.movieRepository.findById(movieId);
		if(movie.isPresent() == false) {
			throw new Exception("Movie with id: " + movieId + " is not found.");
		}
		//----------------------------------------------------------------------------------
		
		// Remove Movie from User Bookmarks
		user.get().removeFromBookmarks(movie.get());
		
		// Update User
		UserDTO updatedUser = this.userRepository.save(user.get());
		
		return updatedUser.getBookmarkedMovies();
	}
	
	public List<MovieDTO> getWatchedMovies(Long userId) {
		
		Optional<UserDTO> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			return null;
		}
		
		List<WatchedMovie> watchedMovies = user.get().getWatchedMovies();
		List<MovieDTO> movies = new ArrayList<MovieDTO>(watchedMovies.size());
		MovieDTO movie = null;
		for (WatchedMovie watchedMovie : watchedMovies) {
			movie = watchedMovie.getMovie();
			movies.add(movie);
		}
		
		return movies;
		
	}
	
	
	public UserDTO addWatchedMovieToUser(Long userId, Long movieId) throws Exception {
		
		Optional<UserDTO> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			throw new Exception("User with id: " + userId + " is not found!");
		}
		
		Optional<MovieDTO> movie = this.movieRepository.findById(movieId);
		if(movie.isPresent() == false) {
			throw new Exception("Movie with id: " + movieId + " is not found!");
		}
		
		user.get().addWatchedMovie(movie.get());
		UserDTO savedUser = this.userRepository.save(user.get());
		
		return savedUser;
	}
	
	public boolean checkEmail(String email) { 
		
		boolean exists = this.userRepository.existsUserByUserInfo_Email(email);
		return exists;
		
	}
	
	public UserDTO getUserByEmail(String email) {
		
		Optional<UserDTO> user = this.userRepository.findByUserInfo_Email(email);
		if(user.isPresent() == false) {
			return null;
		}
		
		return user.get();
		
	}
	
	
	public Boolean exists(UserDTO userDTO) {
		
		if(this.userRepository.existsById(userDTO.getId()) == true) {
			return true;
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
