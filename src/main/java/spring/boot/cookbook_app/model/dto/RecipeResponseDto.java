package spring.boot.cookbook_app.model.dto;

import lombok.Data;

@Data
public class RecipeResponseDto {
    private Long id;
    private String name;
    private String dateCreated;
    private String description;
    private Long parentId;
}
