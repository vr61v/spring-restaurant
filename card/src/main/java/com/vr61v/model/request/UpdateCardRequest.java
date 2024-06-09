package com.vr61v.model.request;

import com.vr61v.model.CardType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UpdateCardRequest (

        UUID userId,

        @Pattern(regexp = "^[0-9]{16}$")
        String number,

        CardType type,

        @Min(0) @Max(1)
        Float discount

) {

}
