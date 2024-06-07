package com.vr61v.model.request;

import jakarta.validation.constraints.Min;

public record UpdateProductRequest (

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
