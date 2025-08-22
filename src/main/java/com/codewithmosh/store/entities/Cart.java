package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Setter
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created",insertable = false, updatable = false) // ignore this field while generating SQL statements
    private LocalDate dateCreated;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE,fetch = FetchType.EAGER,orphanRemoval = true)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            total = total.add(cartItem.getTotalPrice());
        }
        return total;
    }

    public CartItem getItem(Long productId) {
        return cartItems.stream().filter(cartItem -> cartItem.getProduct().getId().equals(productId)).findFirst().orElse(null);
    }

    public CartItem addItem(Product product) {
        var cartItem = getItem(product.getId());
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else {
            cartItem = new CartItem();
            cartItem.setQuantity(1);
            cartItem.setProduct(product);
            cartItem.setCart(this);
            cartItems.add(cartItem);
        }

        return cartItem;
    }

    public void removeItem(Long productId) {
        var cartItem = getItem(productId);
        if (cartItem != null) {
            cartItems.remove(cartItem);
            cartItem.setQuantity(null);
        }
    }

    public void clear() {
        cartItems.clear();
    }
}