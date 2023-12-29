package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.AuthRequest;
import com.enigma.tokopakedi.model.UserResponse;

public interface AuthService {
    UserResponse register(AuthRequest request);
    UserResponse registerAdmin(AuthRequest request);

    String login(AuthRequest request);
}
