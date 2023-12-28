package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.constan.ERole;
import com.enigma.tokopakedi.entity.Role;
import com.enigma.tokopakedi.repository.RoleRepository;
import com.enigma.tokopakedi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role getOrSave(ERole role) {
        Optional<Role> optionalRole = roleRepository.findByRole(role);
        if (optionalRole.isPresent()) return optionalRole.get();
        Role buildRole = Role.builder()
                .role(role)
                .build();
        return roleRepository.saveAndFlush(buildRole);
    }
}
