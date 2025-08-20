package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder // annotate this dto as a builder for cart since we have used builder.default
@AllArgsConstructor
@Getter
@Setter
public class CartDto {
    private UUID id;

    @Builder.Default // use this annotation for default values in dtos
    private List<CartItemDto> items = new ArrayList<>();

    @Builder.Default
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
