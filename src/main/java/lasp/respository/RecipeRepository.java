package lasp.respository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lasp.model.Recipe;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, Integer> {

}
