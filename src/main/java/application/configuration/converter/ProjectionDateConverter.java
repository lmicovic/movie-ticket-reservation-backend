package application.configuration.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.model.ProjectionDate;
import jakarta.persistence.AttributeConverter;

public class ProjectionDateConverter implements AttributeConverter<ProjectionDate, String>{

	public String convertToDatabaseColumn(ProjectionDate attribute) {
		
		if(attribute == null) {
			return null;
		}
		
		try {
			return new ObjectMapper().writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ProjectionDate convertToEntityAttribute(String dbData) {

		if(dbData == null) {
			return null;
		}
		
		try {
			
			return new ObjectMapper().readValue(dbData, ProjectionDateConverter.typeReference());
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		

	}
	
	private static final TypeReference<ProjectionDate> typeReference() {
		return new TypeReference<ProjectionDate>() { };
	}
	
}
