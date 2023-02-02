package spring.boot.cookbook_app.service;

import spring.boot.cookbook_app.model.Recipe;
import java.util.List;

public interface RecipeService {

    Recipe save(Recipe recipe);

    Recipe findById(Long id);

    List<Recipe> findByIdOnAnyDepth(Long id);

    List<Recipe> findAllRecipesInOrderName();

    void deleteById(Long id);
}
