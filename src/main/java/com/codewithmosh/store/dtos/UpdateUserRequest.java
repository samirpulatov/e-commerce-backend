package com.codewithmosh.store.dtos;

import lombok.Data;

@Data
public class UpdateUserRequest {
    public String username;
    public String email;
}
