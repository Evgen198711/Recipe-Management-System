package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.pojo.RecipeUser;

import java.util.Optional;
@Repository
public interface RecipeUserRepository extends CrudRepository<RecipeUser, Long> {
    Optional<RecipeUser> findRecipeUserByEmail(String email);
}
