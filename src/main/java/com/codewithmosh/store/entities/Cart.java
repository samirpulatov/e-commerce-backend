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


    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            total = total.add(cartItem.getTotalPrice());
        }
        return total;
    }
}