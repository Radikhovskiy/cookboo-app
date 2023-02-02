package spring.boot.cookbook_app.model.dto;

import lombok.Data;

@Data
public class ChildRecipeRequestDto {
    private String partOfName;
    private String description;
}
