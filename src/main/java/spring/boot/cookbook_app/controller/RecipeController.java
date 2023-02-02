package spring.boot.cookbook_app.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.cookbook_app.model.Recipe;
import spring.boot.cookbook_app.model.dto.ChildRecipeRequestDto;
import spring.boot.cookbook_app.model.dto.RecipeRequestDto;
import spring.boot.cookbook_app.model.dto.RecipeResponseDto;
import spring.boot.cookbook_app.service.RecipeService;
import spring.boot.cookbook_app.service.mapper.RecipeMapper;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {
    private RecipeService service;
    private RecipeMapper mapper;

    @GetMapping
    @ApiOperation("Find all recipes")
    public List<RecipeResponseDto> findAll() {
        return service.findAllRecipesInOrderName().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation("Find recipe by id with its previous version as id")
    public RecipeResponseDto findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id));
    }

    @GetMapping("/deep/{id}")
    @ApiOperation("Find recipe by id with its previous versions as Recipe")
    public List<RecipeResponseDto> findByIdOnAnyDepth(@PathVariable Long id) {
        return service.findByIdOnAnyDepth(id).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping()
    @ApiOperation("Create a new recipe")
    public RecipeResponseDto save(@RequestBody RecipeRequestDto recipeDto) {
        Recipe savedRecipe = service.save(mapper.toModel(recipeDto));
        return mapper.toDto(savedRecipe);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Update recipe by id")
    public RecipeResponseDto update(@PathVariable Long id, @RequestBody RecipeRequestDto recipeDto) {
        Recipe updatedRecipe = service.save(mapper.toModel(id, recipeDto));
        return mapper.toDto(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete recipe by id")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PostMapping("/{id}")
    @ApiOperation("Create a new recipe based on another one, partOfName will ")
    public RecipeResponseDto saveBasedOn(@PathVariable @ApiParam("Parent") Long id,
                                         @RequestBody ChildRecipeRequestDto childRecipeDto) {
        return mapper.toDto(service.save(mapper.toChildModel(id, childRecipeDto)));
    }
}
