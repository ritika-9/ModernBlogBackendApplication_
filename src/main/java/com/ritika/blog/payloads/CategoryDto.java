package com.ritika.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;
    @NotBlank
    @Size(min = 5, max = 50,message="Minimum size of title is 5")
    private String categoryTitle;
    @NotBlank
    @Size(min = 5, max = 50,message="Minimum size of title is 5")
    private String categoryDescription;

}
