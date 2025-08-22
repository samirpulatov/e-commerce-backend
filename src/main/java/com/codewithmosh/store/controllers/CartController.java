package com.codewithmosh.store.controllers;



import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;

import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import com.codewithmosh.store.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@ResponseStatus(HttpStatus.CREATED)
@Tag(name = "Carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {

        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder){

        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Adds a product to the cart")
    public ResponseEntity<CartItemDto> addProduct(@Parameter(description = "The ID of the cart")
            @PathVariable UUID cartId, @RequestBody AddItemToCartRequest addItemToCartRequest){
        var cartItemDto = cartService.addToCart(cartId,addItemToCartRequest.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable UUID cartId){
        return  cartService.getCart(cartId);

    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateCartItem(@PathVariable UUID cartId, @PathVariable Long productId,
                                                      @Valid @RequestBody UpdateCartItemRequest updateCartItemRequest){
       return  cartService.updateCartItem(cartId,productId,updateCartItemRequest.getQuantity());

    }

    @DeleteMapping("{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable UUID cartId, @PathVariable Long productId){
        cartService.deleteCartItem(cartId,productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{cartId}/items")
    public ResponseEntity<?> clearCart(@PathVariable UUID cartId){

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","Product not found in the cart"));
    }

}
