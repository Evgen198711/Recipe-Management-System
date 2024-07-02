package recipes.business;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.adapters.RecipeUserAdapter;
import recipes.persistence.RecipeUserRepository;
import recipes.pojo.RecipeUser;
@Service
public class RecipeUserDetailsServiceImpl implements UserDetailsService {
    private final RecipeUserRepository repository;

    public RecipeUserDetailsServiceImpl (RecipeUserRepository repository) {
        this.repository = repository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        RecipeUser user = repository
                .findRecipeUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found!"));

        return new RecipeUserAdapter(user);
    }

    public void saveUser(RecipeUser user) {
        repository.save(user);
    }

    public RecipeUser findUserByEmail(String email) throws UsernameNotFoundException {
        return repository.findRecipeUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No such user registered!"));
    }
}
