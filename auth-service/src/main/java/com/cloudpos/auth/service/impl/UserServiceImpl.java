package com.cloudpos.auth.service.impl;

import com.cloudpos.auth.entity.User;
import com.cloudpos.auth.exception.ResourceNotFoundException;
import com.cloudpos.auth.repository.UserRepository;
import com.cloudpos.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findActiveByEmail(String email) {
        return userRepository.findByEmail(email)
                .filter(user -> Boolean.TRUE.equals(user.getActive()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
