package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemToCartRequest {
    @NotNull
    private Long productId;
}
