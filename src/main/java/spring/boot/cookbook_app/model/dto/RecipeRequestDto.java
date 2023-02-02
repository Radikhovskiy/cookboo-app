package spring.boot.cookbook_app.model.dto;

import lombok.Data;

@Data
public class RecipeRequestDto {
    private String name;
    private String description;
}
