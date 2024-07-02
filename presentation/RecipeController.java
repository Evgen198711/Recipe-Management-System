package recipes.presentation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.business.RecipeService;
import recipes.business.RecipeUserDetailsServiceImpl;
import recipes.pojo.Recipe;
import recipes.pojo.RecipeUser;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Map;

@Validated
@RestController
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeUserDetailsServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    record RegistrationRequest(String email, String password, String authority) {
    }

    public RecipeController(RecipeService recipeService,
                            RecipeUserDetailsServiceImpl userService,
                            PasswordEncoder passwordEncoder) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody @Valid RecipeUser request) {

        var user = new RecipeUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            userService.loadUserByUsername(user.getEmail());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);

        } catch (UsernameNotFoundException ex) {
            userService.saveUser(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(null);
        }

    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> saveRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails details) {
        RecipeUser user = userService.findUserByEmail(details.getUsername());
        recipe.setUser(user);
        Recipe receivedRecipe = recipeService.save(recipe);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("id", receivedRecipe.getId()));
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable long id) {
        try {
            Recipe recipe = recipeService.getRecipe(id).orElseThrow(InstanceNotFoundException::new);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(recipe);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("");
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id, @AuthenticationPrincipal UserDetails details) {
        if (recipeService.findRecipeById(id).isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        if (recipeService.findRecipeById(id).orElseThrow().getUser().getId() == userService.findUserByEmail(details.getUsername()).getId()) {

                recipeService.deleteRecipeById(id);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("");

        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Only an author of a recipe can delete a recipe");
        }
    }

    @GetMapping("/api/recipe/search/")
    public ResponseEntity<?> getRecipesByCategory(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {

        if ((category == null && name == null) || (category != null && name != null)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("");
        }
        List<Recipe> recipes = null;

        if (category != null) {
            recipes = recipeService.getRecipesByCategory(category);
        }

        if (name != null) {
            recipes = recipeService.getRecipesByName(name);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipes);
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails details) {

        if (recipeService.findRecipeById(id).isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        if (recipeService.findRecipeById(id).orElseThrow().getUser().getId() == userService.findUserByEmail(details.getUsername()).getId()) {

                recipeService.updateRecipe(recipe, id);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(null);

        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Only an author of a recipe can update a recipe");
        }

    }
}