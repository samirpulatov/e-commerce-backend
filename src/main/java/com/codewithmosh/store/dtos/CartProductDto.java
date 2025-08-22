package com.codewithmosh.store.dtos;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
public class CartProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
