package spring.boot.cookbook_app.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import spring.boot.cookbook_app.model.Recipe;
import spring.boot.cookbook_app.model.dto.ChildRecipeRequestDto;
import spring.boot.cookbook_app.model.dto.RecipeRequestDto;
import spring.boot.cookbook_app.model.dto.RecipeResponseDto;
import spring.boot.cookbook_app.service.RecipeService;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class RecipeMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");

    private final RecipeService service;

    public Recipe toModel(RecipeRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        return recipe;
    }

    public Recipe toModel(Long id, RecipeRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        return recipe;
    }

    public Recipe toChildModel(Long parentId, ChildRecipeRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setName(addPartOfName(parentId, dto));
        recipe.setDescription(dto.getDescription());
        recipe.setParentId(parentId);
        return recipe;
    }

    public RecipeResponseDto toDto(Recipe recipe) {
        RecipeResponseDto dto = new RecipeResponseDto();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setDateCreated(recipe.getDateCreated().format(formatter));
        dto.setParentId(recipe.getParentId());
        return dto;
    }

    private String addPartOfName(Long parentId, ChildRecipeRequestDto dto) {
        String parentName = service.findById(parentId).getName();
        String partOfName = dto.getPartOfName().trim();
        String correctedPartOfName = " " + partOfName.substring(0, 1).toLowerCase()
                + partOfName.substring(1);
        return parentName + correctedPartOfName;
    }
}
