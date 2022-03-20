package io.my.messagesystem.service;

import io.my.messagesystem.dto.UserRegistrationDto;
import io.my.messagesystem.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User saveUser(UserRegistrationDto registrationDto);

    User findByUsername(String username);

}
