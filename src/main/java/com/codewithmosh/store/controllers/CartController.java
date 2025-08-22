package com.codewithmosh.store.controllers;



import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartItemRepository;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
@ResponseStatus(HttpStatus.CREATED)
public class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartController(CartRepository cartRepository, CartMapper cartMapper, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder){
        var cart = cartRepository.save(new Cart());
        var cartDto = cartMapper.toCartDto(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addProduct(@PathVariable UUID cartId, @RequestBody AddItemToCartRequest addItemToCartRequest){
       var cart = cartRepository.findById(cartId).orElse(null);
       if (cart == null) {
           return ResponseEntity.notFound().build();
       }
       var product = productRepository.findById(addItemToCartRequest.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }

        var cartItem = cart.getCartItems().stream().
                filter( item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else {
            cartItem = new CartItem();
            cartItem.setQuantity(1);
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }

        cartRepository.save(cart);

        var cartItemDto = cartMapper.toDto(cartItem);


        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        var cartDto = cartMapper.toCartDto(cart);

        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable UUID cartId, @PathVariable Long productId,
                                                      @RequestBody UpdateCartItemRequest updateCartItemRequest){
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            System.out.println("cart is null");
            return ResponseEntity.notFound().build();
        }
        var cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (cartItem == null) {
            System.out.println("cartItem is null");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var quantity = updateCartItemRequest.getQuantity();
        if(quantity < 1 || quantity> 100){
            return ResponseEntity.badRequest().build();
        }
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return ResponseEntity.ok(cartMapper.toDto(cartItem));


    }



}
