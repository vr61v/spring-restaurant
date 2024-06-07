package com.vr61v.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateProductRequest (

        @NotBlank
        String name,

        @Min(0)
        Double price,

        @Min(0)
        Double weight,

        String category,

        String composition,

        String description

) {
    
}
