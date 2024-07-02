package recipes.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.pojo.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

   List<Recipe> findRecipeByCategoryAllIgnoreCaseOrderByDateDesc(String category);

   List<Recipe> findRecipeByNameContainingIgnoreCaseOrderByDateDesc(String name);

}
