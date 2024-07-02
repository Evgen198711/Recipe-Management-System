package recipes.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "recipes")
public class Recipe {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@JsonIgnore
private Long id;

@JsonIgnore
@ManyToOne
private RecipeUser user;

@Column(name = "name", length = 64)
@NotBlank(message = "Name should not be blank")
private String name;

@Column(name = "category", length = 64)
@NotBlank
private String category;


@UpdateTimestamp
@Temporal(TemporalType.TIMESTAMP)
@Column(name = "date")
private LocalDateTime date;

@Column(name = "description")
@NotBlank(message = "Description should not be blank")
private String description;

@Column(name = "ingredients")
@Size(min = 1)
@NotNull(message = "Ingredients should not be blank")
@ElementCollection
private List<String> ingredients;


@Column(name = "directions")
@Size(min = 1)
@NotNull(message = "Directions should not be blank")
@ElementCollection
private List<String> directions;


}
