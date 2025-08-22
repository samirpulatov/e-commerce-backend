package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem,Long> {
}
