package lasp.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@Document(collection = "Recipe")
public class Recipe {
	@Id
	private String id;
	
	private String name;
	
	private String description;
	
	private String[] imageUrl;
	
	private String[] cuisineType;
	
	private String courseType;
	
	private String difficulty;
	
	private String[] tags;
	
	private String[] technique;
	
	private Object prepTime;
	
	private Object noOfServings;
	
	private Object calories;
	
	private List<Ingredient> ingredients;

	private String[] prepSteps;
	
	private List<Nutrition> nutrition;
	
	private String recipePublisher;
	
	private String recipePublishDate;
	
	private String[] source;

	public Recipe(String name, String description, String[] imageUrl, String[] cuisineType, String courseType,
			String difficulty, String[] tags, String[] technique, Object prepTime, Object noOfServings, Object calories,
			List<Ingredient> ingredients, String[] prepSteps, List<Nutrition> nutrition, String recipePublisher,
			String recipePublishDate, String[] source) {
		super();
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.cuisineType = cuisineType;
		this.courseType = courseType;
		this.difficulty = difficulty;
		this.tags = tags;
		this.technique = technique;
		this.prepTime = prepTime;
		this.noOfServings = noOfServings;
		this.calories = calories;
		this.ingredients = ingredients;
		this.prepSteps = prepSteps;
		this.nutrition = nutrition;
		this.recipePublisher = recipePublisher;
		this.recipePublishDate = recipePublishDate;
		this.source = source;
	}
	

	
	

	
}
