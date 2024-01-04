package com.rentacar.controllers;

import com.rentacar.dtos.SignupRequest;
import com.rentacar.dtos.UserDto;
import com.rentacar.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;
@PostMapping("/signup")
    public ResponseEntity<?>  createCustomer(@RequestBody SignupRequest signupRequest){
      if (authService.hasCustomerWithEmail(signupRequest.getEmail()))
          return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email already exits. Try again with another email!");

    UserDto createdUserDto= authService.createCustomer(signupRequest);
   if(createdUserDto ==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request!");
   return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);

    }
}
