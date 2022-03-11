package com.wicc.brs.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;

    @NotEmpty(message = "Name can not be Empty")
    private String name;

    @NotEmpty(message = "Description can not be Empty")
    private String description;
}
