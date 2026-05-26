package com.cloudpos.auth.service;

import com.cloudpos.auth.entity.User;

public interface UserService {

    User findActiveByEmail(String email);
}
