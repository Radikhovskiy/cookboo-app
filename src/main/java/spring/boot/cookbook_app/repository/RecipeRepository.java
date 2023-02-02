package spring.boot.cookbook_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.cookbook_app.model.Recipe;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query(value = "SELECT r FROM Recipe r ORDER BY name")
    List<Recipe> findAllRecipesInOrderName();

    List<Recipe> findAllByParentId(Long id);
}
