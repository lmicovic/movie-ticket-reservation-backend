package application.configuration.deserializer;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import application.model.ProjectionDTO;
import application.model.ReservationDTO;
import application.model.UserDTO;
import application.repository.ProjectionRepository;
import application.repository.UserRepository;

public class ReservationDTODeserializer extends JsonDeserializer<ReservationDTO> {
	
	private UserRepository userRepository;
	private ProjectionRepository projectionRepository;
	
	@Autowired
	public ReservationDTODeserializer(UserRepository userRepository, ProjectionRepository projectionRepository) {
		this.userRepository = userRepository;
		this.projectionRepository = projectionRepository;
	}
	
	@SuppressWarnings({ "removal", "unchecked" })
	@Override
	public ReservationDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = p.getCodec().readTree(p);
			
//			System.out.println(node.toPrettyString());
			
			Optional<UserDTO> user = this.userRepository.findById(new Long(mapper.treeToValue(node.get("_user").get("id"), Long.class)));
			if(user.isPresent() == false) {
				throw new Exception("User with id: " + mapper.treeToValue(node.get("_user").get("id"), Long.class) + " is not found.");
			}
			
			Optional<ProjectionDTO> projection = this.projectionRepository.findById(new Long(mapper.treeToValue(node.get("_projection").get("id"), Long.class)));
			if(projection.isPresent() == false) {
				throw new Exception("Projection with id: " + mapper.treeToValue(node.get("_projection").get("id"), Long.class) + " is not found.");
			}
			
			System.out.println(user.get());
			System.out.println(projection.get());
			
			Integer ticketCount = mapper.treeToValue(node.get("_ticketCount"), Integer.class);
			Set<Integer> reservedSeats = mapper.treeToValue(node.get("_reservedSeats"), Set.class);
			Date reservationDate = mapper.treeToValue(node.get("_reservationDate"), Date.class);
			Float totalPrice = mapper.treeToValue(node.get("_totalPrice"), Float.class);	
			
			ReservationDTO reservation = new ReservationDTO(user.get(), projection.get(), ticketCount, totalPrice, reservedSeats, reservationDate);	

			return reservation;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
