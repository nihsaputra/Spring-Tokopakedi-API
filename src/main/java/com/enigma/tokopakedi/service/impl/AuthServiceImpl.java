package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.constan.ERole;
import com.enigma.tokopakedi.entity.Role;
import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.AuthRequest;
import com.enigma.tokopakedi.model.UserResponse;
import com.enigma.tokopakedi.repository.UserCredentialRepository;
import com.enigma.tokopakedi.service.AuthService;
import com.enigma.tokopakedi.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initSuperAdmin() {
        Optional<UserCredential> optionalUserCredential = userCredentialRepository.findByEmail("superadmin@gmail.com");
        if (optionalUserCredential.isPresent())return;

        Role roleSuperAdmin = roleService.getOrSave(ERole.ROLE_SUPER_ADMIN);
        Role roleAdmin = roleService.getOrSave(ERole.ROLE_ADMIN);
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        String hashPassword = passwordEncoder.encode("password");

        UserCredential userCredential = UserCredential.builder()
                .email("superadmin@gmail.com")
                .password(hashPassword)
                .roles(List.of(roleCustomer,roleAdmin,roleSuperAdmin))
                .build();

        userCredentialRepository.saveAndFlush(userCredential);
    }

    @Override
    public UserResponse register(AuthRequest request) {
        Optional<UserCredential> optionalUserCredential = userCredentialRepository.findByEmail(request.getEmail());
        if (optionalUserCredential.isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT, "email is already exist");

        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserCredential userCredential = UserCredential.builder()
                .email(request.getEmail())
                .password(hashPassword)
                .roles(List.of(roleCustomer))
                .build();

        userCredentialRepository.saveAndFlush(userCredential);

        List<String> roles = toUserResponse(userCredential);

        UserResponse userResponse = UserResponse.builder()
                .email(userCredential.getEmail())
                .roles(roles)
                .build();

        return userResponse;
    }

    @Override
    public UserResponse registerAdmin(AuthRequest request) {
        Optional<UserCredential> optionalUserCredential = userCredentialRepository.findByEmail(request.getEmail());
        if (optionalUserCredential.isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT, "email is already exist");

        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        Role roleAdmin = roleService.getOrSave(ERole.ROLE_SUPER_ADMIN);

        String hashPassword = passwordEncoder.encode(request.getPassword());
        UserCredential userCredential = UserCredential.builder()
                .email(request.getEmail())
                .password(hashPassword)
                .roles(List.of(roleAdmin,roleCustomer))
                .build();

        userCredentialRepository.saveAndFlush(userCredential);

        List<String> roles = toUserResponse(userCredential);

        UserResponse userResponse = UserResponse.builder()
                .email(request.getEmail())
                .roles(roles)
                .build();

        return userResponse;
    }

    private static List<String> toUserResponse(UserCredential userCredential) {
        List<String> roles = userCredential.getRoles().stream()
                .map(role -> role.getRole().name()).toList();
        return roles;
    }
}
