package lasp;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import lasp.model.Ingredient;
import lasp.model.Recipe;
import lasp.model.Nutrition;
import lasp.respository.RecipeRepository;



@SpringBootApplication
public class MlApiMongoDbApplication implements CommandLineRunner {
	
	@Autowired
	private RecipeRepository recipeRepository;

	public static void main(String[] args) {
		SpringApplication.run(MlApiMongoDbApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Trying out Test API");
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://yummly2.p.rapidapi.com/feeds/list?start=206&limit=18tag=list.recipe.popular"))
				.header("x-rapidapi-host", "yummly2.p.rapidapi.com")
				.header("x-rapidapi-key", "855d3d669dmsh53d7f9d7132fe60p1f292ajsnbfa43b8c6908")   
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		
		try {
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			if (response!= null) {
				System.out.println("Response Status Code: " + response.statusCode());
				System.out.println("Source API URL: " + response.uri().toString());
				
		        Object obj = new JSONParser().parse(response.body());
		          
		        // typecasting obj to JSONObject
		        JSONObject jo = (JSONObject) obj;
		        JSONArray  feed = (JSONArray) jo.get("feed");
		        
		       
		        for(Object o : feed) {
		        	
		        	JSONObject e1 = (JSONObject)o;
//name String		        	
		        	String displayName;
		        	if(e1.containsKey("display")) {
		        		Object display1 = e1.get("display");
			        	JSONObject displayjo = (JSONObject) display1;
			        	if(displayjo == null) {
			        		displayName = null;
			        	}else {
			        		if(displayjo.containsKey("displayName")) {
				        		displayName = (String) displayjo.get("displayName");
				        	}else {
				        		displayName = null;
				        	}
			        	}    	
		        	}else {
		        		displayName = null;
		        	}

// description String
		        	
		        	String description;
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
			        	if(contentjo.containsKey("description")) {
			        		JSONObject descriptionjo = (JSONObject) contentjo.get("description");
			        			if(descriptionjo == null) {
			        				description = null;
			        			}else {
			        				if(descriptionjo.containsKey("text")) {
					        			description = (String) descriptionjo.get("text");
					        		}else {
					        			description = null;
					        		}		
			        			}
			        	}else {
			        		description = null;
			        	}
		        	}else {
		        		description = null;
		        	}
		        	
// imgaeUrl String
//TODO		   
		        	String[] imageUrl;
		        	if(e1.containsKey("display")) {
		        		Object display1 = e1.get("display");
			        	JSONObject displayjo = (JSONObject) display1;
			        	
			        	if(displayjo.containsKey("images")) {
			        		JSONArray imagesjo = (JSONArray) displayjo.get("images");
				        	if(imagesjo.size() != 0) {
				        		imageUrl = new String[imagesjo.size()];
				        		for(int i = 0; i < imagesjo.size(); i++) {
				        			imageUrl[i] = (String)imagesjo.get(i);
				        		}
				        	}else {
				        		imageUrl = null;
				        	}
			        	}else {
			        		imageUrl = null;
			        	}
		        	}else {
		        		imageUrl = null;
		        	}
		        	
//cuisineType String[]
		        	
		        	String[] cuisineType;
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
		        		if(contentjo.containsKey("tags")) {
		        			JSONObject tagsjo = (JSONObject) contentjo.get("tags");		        	
				        	if(tagsjo.containsKey("cuisine")) {
				        		JSONArray cuisine = (JSONArray) tagsjo.get("cuisine");		
					        	cuisineType = new String[cuisine.size()];
					        	for(int i = 0; i < cuisine.size(); i++) {
					        		JSONObject cuisineItem = (JSONObject) cuisine.get(i);
					        		Object cuisineName = (Object) cuisineItem.get("display-name");
					        		cuisineType[i] = cuisineName.toString();
					        	}
				        	}else {
				        		cuisineType = null;
				        	}
		        		}else {
		        			cuisineType = null;
		        		} 	
		        	}else {
		        		cuisineType = null;
		        	}
		        	
//courseType String
		        	
		        	String course;
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
		        		if(contentjo.containsKey("tags")) {
		        			JSONObject tagsjo = (JSONObject) contentjo.get("tags");
		        			
		        			if(tagsjo.containsKey("course")) {
		        				JSONArray coursejo = (JSONArray) tagsjo.get("course");
					        	if(coursejo.size() != 0) {
					        		JSONObject courseItem = (JSONObject) coursejo.get(0);
						        	course = (String)courseItem.get("display-name");
					        	}else {
					        		course = null;
					        	}	
		        			}else {
		        				course = null;
		        			}	
		        		}else {
		        			course = null;
		        		}	
		        	}else {
		        		course = null;
		        	}
		        	
// difficulty String		        	
		        	
		        	String difficulty;
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
		        		if(contentjo.containsKey("tags")) {
		        			JSONObject tagsjo = (JSONObject) contentjo.get("tags");	
				        	if(tagsjo.containsKey("difficulty")) {
				        		JSONArray difficultyjo = (JSONArray) tagsjo.get("difficulty");
				        		if(difficultyjo.size() != 0) {
					        		JSONObject difficultyItem = (JSONObject) difficultyjo.get(0);
						        	difficulty = (String)difficultyItem.get("display-name");
					        	}else {
					        		difficulty = null;
					        	}
				        	}else {
				        		difficulty = null;
				        	}
		        		}else {
		        			difficulty = null;
		        		}	
		        	}else {
		        		difficulty = null;
		        	}
	
// tags String[]
		        	String[] tags;
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
		        		if(contentjo.containsKey("tags")) {
		        			JSONObject tagsjo = (JSONObject) contentjo.get("tags");	
				        	if(tagsjo.containsKey("nutrition")) {
				        		JSONArray nutritionjo = (JSONArray) tagsjo.get("nutrition");
				        		if(nutritionjo.size() != 0) {
				        			tags = new String[nutritionjo.size()];
				        			for(int i = 0; i < nutritionjo.size(); i++) {
				        				JSONObject nutritionItem = (JSONObject) nutritionjo.get(i);
				        				if(nutritionItem.containsKey("display-name")) {
				        					tags[i] = (String)nutritionItem.get("display-name");
				        				}else {
				        					tags = null;
				        				}	
				        			}	
					        	}else {
					        		tags = null;
					        	}
				        	}else {
				        		tags = null;
				        	}
		        		}else {
		        			tags = null;
		        		}	
		        	}else {
		        		tags = null;
		        	}
		        	
		        	
		        	
		        	
// 	technique String[]
		        	String[] technique;
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
		        		if(contentjo.containsKey("tags")) {
		        			JSONObject tagsjo = (JSONObject) contentjo.get("tags");	
				        	if(tagsjo.containsKey("technique")) {
				        		JSONArray techniquejo = (JSONArray) tagsjo.get("technique");
				        		if(techniquejo.size() != 0) {
				        			technique = new String[techniquejo.size()];
				        			for(int i = 0; i < techniquejo.size(); i++) {
				        				JSONObject techniqueItem = (JSONObject) techniquejo.get(i);
				        				if(techniqueItem.containsKey("display-name")) {
				        					technique[i] = (String)techniqueItem.get("display-name");
				        				}else {
				        					technique = null;
				        				}	
				        			}	
					        	}else {
					        		technique = null;
					        	}
				        	}else {
				        		technique = null;
				        	}
		        		}else {
		        			technique = null;
		        		}	
		        	}else {
		        		technique = null;
		        	}

		        	
// preparTime Object
// noOfServings	Object	        	
		        	
		        	Object preparTime;
		        	Object noOfServings;
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
		        		if(contentjo.containsKey("details")) {
		        			JSONObject detailsjo = (JSONObject) contentjo.get("details");
		        			if(detailsjo.containsKey("totalTimeInSeconds")) {
				        		preparTime = (Object) detailsjo.get("totalTimeInSeconds");
				        	}else {
				        		preparTime = null;
				        	}
		        			
		        			if(detailsjo.containsKey("numberOfServings")) {
				        		noOfServings = (Object) detailsjo.get("numberOfServings");
				        	}else {
				        		noOfServings = null;
				        	}
		        		}else {
		        			preparTime = null;
			        		noOfServings = null;
		        		}       		
		        	}else {
		        		preparTime = null;
		        		noOfServings = null;
		        	}
		        	
// List<Ingredient> list
		        	
		        	List<Ingredient> ingredientLines = new ArrayList<>(); 	
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
		        		if(contentjo.containsKey("ingredientLines")) {
		        			JSONArray ingredientLinesjo = (JSONArray) contentjo.get("ingredientLines");							
				        	if(ingredientLinesjo.size() != 0) {
				        		
				        		for(int i = 0; i < ingredientLinesjo.size(); i++) {
					        		JSONObject Ingredient = (JSONObject) ingredientLinesjo.get(i);
					        		//ingredient
					        		String content;
					        		if(Ingredient.containsKey("ingredient")) {
					        			content = (String) Ingredient.get("ingredient");
					        		}else {
					        			content = null;
					        		}
					        		
					        		//unit
					        		JSONObject amountjo = (JSONObject)Ingredient.get("amount");
					        		String unit;
					        		if(amountjo.containsKey("metric")) {
					        			JSONObject metricjo = (JSONObject) amountjo.get("metric");
					        			if(metricjo.containsKey("unit")) {
					        				JSONObject unitjo = (JSONObject) metricjo.get("unit");
					        				if(unitjo.containsKey("abbreviation")) {
					        					unit = (String) unitjo.get("abbreviation");
					        				}else {
					        					unit = null;
					        				}
					        			}else {
					        				unit = null;
					        			}	
					        		}else {
					        			unit = null;
					        		}
					        		
					        		
					        		//amount		        		
					        		Object amount;
					        		if(amountjo.containsKey("metric")) {
					        			JSONObject metricjo = (JSONObject) amountjo.get("metric");
					        			if(metricjo.containsKey("quantity")) {
					        				amount = (Object) metricjo.get("quantity");
					        			}else {
					        				amount = null;
					        			}	
					        		}else {
					        			amount = null;
					        		}	
					        		
					        		Ingredient newIngredient = new Ingredient(content, amount, unit);
					        		newIngredient.setIngredient(content);
					        		newIngredient.setAmount(amount);
					        		newIngredient.setUnit(unit);

					        		ingredientLines.add(newIngredient);
					        	}
				        	}else {
				        		ingredientLines = null;
				        	}

			        	}else {
			        		ingredientLines = null;
			        	}
		        	}else {
		        		ingredientLines = null;
		        	}
		        	
// PreSteps String[]		        	
		        	
		        	String[] preparSteps;
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
		        		if(contentjo.containsKey("preparationSteps")) {
		        			JSONArray preparationSteps = (JSONArray) contentjo.get("preparationSteps");
			        		if(preparationSteps == null) {
			        			preparSteps = null;
			        		}else {
			        			if(preparationSteps.size() != 0) {
					        		preparSteps = new String[preparationSteps.size()];
						        	for(int i = 0; i < preparationSteps.size(); i++) {
						        		preparSteps[i] = (String)preparationSteps.get(i);
						        	}
					        	}else {
					        		preparSteps = null;
					        	}
			        		} 		
		        		}else {
		        			preparSteps = null;
		        		}
		        	}else {
		        		preparSteps = null;
		        	}
		        	
// List<Nutrition> list		        	

		        	String attribute = null;
		        	Object value = null;
		        	String abbreviation;
		        	Nutrition nutrition;
		        	List<Nutrition> nutritionList = new ArrayList<>();  			
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		if(contentjo.containsKey("nutrition")) {
		        			JSONObject nutritionjo = (JSONObject) contentjo.get("nutrition");
		        			
		        			if(nutritionjo.containsKey("nutritionEstimates")) {
				        		JSONArray nutritionEstimatesjo = (JSONArray) nutritionjo.get("nutritionEstimates");
				        		if(nutritionEstimatesjo.size() != 0) {
				        			for(int i = 0; i < nutritionEstimatesjo.size(); i++) {
						        		JSONObject nutritionItemjo = (JSONObject)nutritionEstimatesjo.get(i);
						        		// get attribute
						        		if(nutritionItemjo.containsKey("attribute")) {
						        			Object attributejo = (Object)nutritionItemjo.get("attribute");
								        	attribute = (String)attributejo;
						        		}else {
						        			attribute = null;
						        		}

							        	// get value
						        		if(nutritionItemjo.containsKey("value")) {
						        			Object valuejo = (Object)nutritionItemjo.get("value");
								        	value = (Object)valuejo;
						        		}else {
						        			value = null;
						        		}
							        	
							        	// get abbreviation
						        		if(nutritionItemjo.containsKey("unit")) {
						        			JSONObject unitObjectjo = (JSONObject)nutritionItemjo.get("unit");
						        			if(unitObjectjo.containsKey("abbreviation")) {
							        			Object abbreviationjo = (Object)unitObjectjo.get("abbreviation");							        
									        	abbreviation = (String)abbreviationjo;
							        		}else {
							        			abbreviation = null;
							        		}
						        		}else {
						        			abbreviation = null;
						        		}

							        	nutrition = new Nutrition(attribute, value, abbreviation);
							        	
							        	nutritionList.add(nutrition);			    
						        	}
				        		}else {
				        			nutritionList = null;
				        		}
				        		
				        	}else {
				        		nutritionList = null;
				        	}
		        		}else {
		        			nutritionList = null;
		        		}
		        	}else {
		        		nutritionList = null;
		        	}

// calories Object	        	
		        	
		        	Object calories = null;
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
		        		if(contentjo.containsKey("nutrition")) {
		        			JSONObject nutritionjo = (JSONObject) contentjo.get("nutrition");
		        			
		        			if(nutritionjo.containsKey("nutritionEstimates")) {
				        		JSONArray nutritionEstimatesjo = (JSONArray) nutritionjo.get("nutritionEstimates");
				        		if(nutritionEstimatesjo.size() != 0) {
				        			for(int i = 0; i < nutritionEstimatesjo.size(); i++) {
						        		JSONObject caloriesjo = (JSONObject) nutritionEstimatesjo.get(i);
						        		String type = (String)caloriesjo.get("attribute");
						        		if(type.equals("ENERC_KCAL")) {
						        			calories = (Object) caloriesjo.get("value");
						        		}
						        	}
				        		}else {
				        			calories = null;
				        		}
				        	}else {
				        		calories = null;
				        	}
		        		}else {
		        			calories = null;
		        		}
		        	}else {
		        		calories = null;
		        	}
		        	
// recipePublisher String
// recipePublishDate String
		        	
		        	String sourceRecipeUrl;
		        	String sourceDisplayName;
		        	
		        	if(e1.containsKey("display")) {
		        		Object display1 = e1.get("display");
		        		JSONObject displayjo = (JSONObject) display1;
		        		
		        		if(displayjo.containsKey("source")) {
			        		JSONObject sourcejo = (JSONObject) displayjo.get("source");
			        		if(sourcejo.containsKey("sourceDisplayName")) {
			        			sourceDisplayName = (String) sourcejo.get("sourceDisplayName");
			        		}else {
			        			sourceDisplayName = null;
			        		}	
			        	}else {
			        		sourceDisplayName = null;
			        	}
		        	}else {
		        		sourceDisplayName = null;
		        	}
		        	
		        	
		        			
		        	if(e1.containsKey("seo")) {
		        		JSONObject seojo = (JSONObject) e1.get("seo");
		        		
		        		if(seojo.containsKey("firebase")) {
		        			JSONObject firebasejo = (JSONObject) seojo.get("firebase");
		        			if(firebasejo.containsKey("webUrl")) {
		        				sourceRecipeUrl = (String) firebasejo.get("webUrl");
		        			}else {
		        				sourceRecipeUrl = null;
		        			}
		        		}else {
		        			sourceRecipeUrl = null;
		        		}
		        	}else {
		        		sourceRecipeUrl = null;
		        	}

		        	String[] source = {sourceDisplayName, sourceRecipeUrl};

// source String[]		        	
		        	
// recipePublisher String		        	
		        	String recipePublisher = null;
		        	
		        	
// recipePubliDate String
		        	String recipePubliDate = null;
		        	if(e1.containsKey("content")) {
		        		JSONObject contentjo = (JSONObject) e1.get("content");
		        		
		        		if(contentjo.containsKey("videos")) {
		        			JSONObject videosjo = (JSONObject) contentjo.get("videos");
		        			if(videosjo.containsKey("createTime")) {
		        				recipePubliDate = (String) videosjo.get("createTime");
				        	}else {
				        		recipePubliDate = null;
				        	}
		        		}else {
		        			recipePubliDate = null;
		        		}       		
		        	}else {
		        		recipePubliDate = null;
		        	}
		        	
		        	
		        	
//		        	System.out.println("start to connect");
		        	
		        	Recipe recipe = new Recipe(displayName, 
							description, 
							imageUrl,		        							
							cuisineType, 
							course, 
							difficulty,
							tags,
							technique,
							preparTime, 
							noOfServings, 
							calories, 
							ingredientLines, 
							preparSteps, 
							nutritionList,
							recipePublisher,
							recipePubliDate,
							source);

		        	
		        	recipeRepository.save(recipe);
		        	System.out.println(recipe.toString());
		        	
		        	
		        }
		    
				System.out.println("successfully");
			}
							
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
