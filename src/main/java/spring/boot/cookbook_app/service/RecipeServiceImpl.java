package spring.boot.cookbook_app.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.cookbook_app.model.Recipe;
import spring.boot.cookbook_app.repository.RecipeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository repository;

    @Override
    public Recipe save(Recipe recipe) {
        recipe.setDateCreated(LocalDateTime.now());
        return repository.save(recipe);
    }

    @Override
    public Recipe findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Can't find recipe by id: " + id));
    }

    public List<Recipe> findByIdOnAnyDepth(Long id) {
        List<Recipe> recipeChain = new ArrayList<>();
        Recipe recipe;
        while (id != null) {
            recipe = repository.findById(id).get();
            recipeChain.add(repository.findById(id).get());
            id = recipe.getParentId();
        }
        return recipeChain;
    }

    @Override
    public List<Recipe> findAllRecipesInOrderName() {
        return repository.findAllRecipesInOrderName();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
        repository.findAllByParentId(id).forEach((recipe) -> {
            recipe.setParentId(null);
            save(recipe);
        });
    }
}
