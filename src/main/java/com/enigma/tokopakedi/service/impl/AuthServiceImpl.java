package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.constan.ERole;
import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Role;
import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.helper.ValidationUtil;
import com.enigma.tokopakedi.model.AuthRequest;
import com.enigma.tokopakedi.model.UserResponse;
import com.enigma.tokopakedi.repository.UserCredentialRepository;
import com.enigma.tokopakedi.security.JwtUtil;
import com.enigma.tokopakedi.service.AuthService;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;
    private final ValidationUtil validationUtil;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse register(AuthRequest request) {
        validationUtil.validate(request);

        Optional<UserCredential> optionalUserCredential = userCredentialRepository.findByEmail(request.getEmail());

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

        Customer customer = Customer.builder()
                .userCredential(userCredential)
                .build();

        customerService.createNew(customer);

        return userResponse;
    }

    @Override
    public UserResponse registerAdmin(AuthRequest request) {
        validationUtil.validate(request);

        Optional<UserCredential> optionalUserCredential = userCredentialRepository.findByEmail(request.getEmail());

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

    @Override
    public String login(AuthRequest request) {
        validationUtil.validate(request);

        // login dari spring security untuk authentication
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // simpan sesi user di database spring boot sementara, bertujuan untuk mengakses resource tertentu di kemudian
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // mengambil principal dan di jadikan UserCredential untuk di generate dijadikan token
        UserCredential userCredential = (UserCredential) authentication.getPrincipal();

        return jwtUtil.generateToken(userCredential);
    }

    private static List<String> toUserResponse(UserCredential userCredential) {
        List<String> roles = userCredential.getRoles().stream()
                .map(role -> role.getRole().name()).toList();

        UserResponse userResponse = UserResponse.builder()
                .email(userCredential.getEmail())
                .roles(roles)
                .build();

        return roles;
    }
}
