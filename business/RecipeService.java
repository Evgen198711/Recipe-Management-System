package recipes.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipes.exceptions.NoSuchIdException;
import recipes.persistence.RecipeRepository;
import recipes.pojo.Recipe;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe save(Recipe recipe) {
     return recipeRepository.save(recipe);
    }

    public Optional<Recipe> getRecipe(long id) {
        return recipeRepository.findById(id);
    }

    public void deleteRecipeById(long id) {
       recipeRepository.deleteById(id);
    }

    public List<Recipe> getRecipesByCategory(String category) {
       return recipeRepository.findRecipeByCategoryAllIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> getRecipesByName(String name) {
        return recipeRepository.findRecipeByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public Optional<Recipe> findRecipeById(long id) {
        return recipeRepository.findById(id);
    }

@Transactional
    public void updateRecipe(Recipe recipe, long id) {

            Recipe updatedRecipe = recipeRepository.findById(id).orElseThrow(NoSuchIdException::new);


            updatedRecipe.setName(recipe.getName());
            updatedRecipe.setCategory(recipe.getCategory());
            updatedRecipe.setDescription(recipe.getDescription());
            updatedRecipe.setIngredients(recipe.getIngredients());
            updatedRecipe.setDirections(recipe.getDirections());

            recipeRepository.save(updatedRecipe);

    }
}
