package recipes.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class RecipeUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "\\w+@\\w+\\..{2,}")
    @Column(name = "email", unique = true)
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    @Column(name = "password")
    private String password;

}
