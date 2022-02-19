package lasp.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document
public class Nutrition {
	
	private String attribute;
	
	private Object value;
		
	private String abbreviation;   //from unit
	
}
