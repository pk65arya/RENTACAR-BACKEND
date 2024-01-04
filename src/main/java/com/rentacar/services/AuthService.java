package com.rentacar.services;

import com.rentacar.dtos.SignupRequest;
import com.rentacar.dtos.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);
 boolean hasCustomerWithEmail(String email);

}
