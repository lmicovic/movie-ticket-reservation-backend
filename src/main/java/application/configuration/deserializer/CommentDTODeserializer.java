package application.configuration.deserializer;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.model.CommentDTO;
import application.model.MovieDTO;
import application.model.UserDTO;
import application.service.MovieService;
import application.service.UserService;

public class CommentDTODeserializer extends JsonDeserializer<CommentDTO> {
	
	private UserService userService;
	private MovieService movieService;

	@Autowired
	public CommentDTODeserializer(UserService userService, MovieService movieService) {
		this.userService = userService;
		this.movieService = movieService;
	}
	
	@Override
	public CommentDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = p.getCodec().readTree(p);
		
		System.out.println("test");
		System.out.println(node.toPrettyString());
		
		UserDTO user = mapper.treeToValue(node.get("_user"), UserDTO.class);
		MovieDTO movie = mapper.treeToValue(node.get("_movie"), MovieDTO.class);
		
		System.out.println(user);
		
		user.setImage(this.userService.getById(user.getId()).getImage());
		movie.setImage(this.movieService.getById(movie.getId()).getImage());
		
		Date commentDate = mapper.treeToValue(node.get("_date"), Date.class);
		Integer rating = node.get("_rating").asInt();
		String commentText = node.get("_commentText").asText();
		
		CommentDTO comment = new CommentDTO(user, movie, commentDate, rating, commentText);
		
		return comment;
	}
	
}
