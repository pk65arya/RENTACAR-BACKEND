package com.rentacar.dtos;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;

    private String name;

    private String password;
}
