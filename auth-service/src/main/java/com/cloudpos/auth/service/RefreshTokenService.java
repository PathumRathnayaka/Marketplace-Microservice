package com.cloudpos.auth.service;

import com.cloudpos.auth.entity.RefreshToken;
import com.cloudpos.auth.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken validateRefreshToken(String refreshToken);

    void revokeRefreshToken(String refreshToken);
}
