package com.codewithmosh.store.mappers;


import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.CartProductDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import com.codewithmosh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items", source = "cartItems" )
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())" )
    CartDto toCartDto(Cart cart);

    @Mapping(source = "product", target = "product")
    @Mapping(target = "totalPrice",expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    CartProductDto toDto(Product product);


}
