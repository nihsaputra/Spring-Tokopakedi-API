package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.repository.UserCredentialRepository;
import com.enigma.tokopakedi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialRepository userCredentialRepository;

    // verifikasi userId yang ada di token
    @Override
    public UserCredential loadUserById(String userId) {
        return userCredentialRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"unathorized"));
    }

    // atuhontication spring security -> login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userCredentialRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"unathorized"));
    }
}
