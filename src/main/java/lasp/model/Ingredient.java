package lasp.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@Document
public class Ingredient {
	
	private String ingredient;
	
	private Object amount;
	
	private String unit;

}
