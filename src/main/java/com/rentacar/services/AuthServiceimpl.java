package com.rentacar.services;

import com.rentacar.dtos.SignupRequest;
import com.rentacar.dtos.UserDto;
import com.rentacar.entities.User;
import com.rentacar.enums.UserRole;
import com.rentacar.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceimpl implements AuthService{
@Autowired
    UserRepository userRepository;

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
      User user=new User();
      user.setEmail(signupRequest.getEmail());
      user.setName(signupRequest.getName());
      user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
      user.setUserRole(UserRole.CUSTOMER);
        User createcustomer =userRepository.save(user);
      UserDto createdUserDto=new UserDto();
createdUserDto.setId(createcustomer.getId());
createdUserDto.setEmail(createcustomer.getEmail());
createdUserDto.setName(createcustomer.getName());
createdUserDto.setUserRole(createcustomer.getUserRole());
        return createdUserDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}

