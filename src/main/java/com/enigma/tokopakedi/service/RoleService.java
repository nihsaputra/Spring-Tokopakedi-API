package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.constan.ERole;
import com.enigma.tokopakedi.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
