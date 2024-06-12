//package application.configuration.deserializer;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.fasterxml.jackson.core.JacksonException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import application.model.MovieDTO;
//import application.model.UserDTO;
//import application.model.WatchedMovie;
//
//
//public class UserDTODeserializer extends JsonDeserializer<UserDTO>{
//	
//	public UserDTODeserializer() {
//
//	}
//	
//	@Override
//	public UserDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
//		
//		try {
//			
//			ObjectMapper mapper = new ObjectMapper();
//			JsonNode node = p.getCodec().readTree(p);
//			
//			Long userId;
//			String firstname; 
//			String lastname;
//			String email;
//			String password;
//			List<MovieDTO> bookmarkedMovies; 
//			List<WatchedMovie> watchedMovies;
//			
//			UserDTO user = new UserDTO();
//			
//			// Id
//			if(node.has("_id")) {
//				userId = mapper.treeToValue(node.get("_id"), Long.class);
//				user.setId(userId);
//			}
//			else if(node.has("id" )) {
//				userId = mapper.treeToValue(node.get("id"), Long.class);
//				user.setId(userId);
//			}
//			else {
//				userId = null;
//			}
//			
//			
//			// Firstname
//			if(node.has("_firstname")) {
//				firstname = mapper.treeToValue(node.get("_firstname"), String.class);
//				user.setFirstname(firstname);
//			}
//			else if(node.has("firstname") ) {
//				firstname = mapper.treeToValue(node.get("firstname"), String.class);
//				user.setFirstname(firstname);
//			}
//			else {
//				firstname = null;
//			}
//			
//			
//			// Lastname
//			if(node.has("_lastname")) {
//				lastname = mapper.treeToValue(node.get("_lastname"), String.class);
//				user.setLastname(lastname);
//			}
//			else if(node.has("lastname") ) {
//				lastname = mapper.treeToValue(node.get("lastname"), String.class);
//				user.setLastname(lastname);
//			}
//			else {
//				lastname = null;
//			}
//			
//			
//			// Email
//			if(node.has("_email")) {
//				email = mapper.treeToValue(node.get("_email"), String.class);
//				user.setEmail(email);
//			}
//			else if(node.has("email") ) {
//				email = mapper.treeToValue(node.get("email"), String.class);
//				user.setEmail(email);
//			}
//			else {
//				email = null;
//			}
//			
//			// Password
//			if(node.has("_password")) {
//				password = mapper.treeToValue(node.get("_password"), String.class);
//				user.setPassword(password);
//			}
//			else if(node.has("password") ) {
//				password = mapper.treeToValue(node.get("password"), String.class);
//				user.setPassword(password);
//			}
//			else {
//				password = null;
//			}
//			
//			return user;
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
//	
//}
