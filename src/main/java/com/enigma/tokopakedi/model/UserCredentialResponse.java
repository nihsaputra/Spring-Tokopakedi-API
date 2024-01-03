package com.enigma.tokopakedi.model;

import com.enigma.tokopakedi.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialResponse {

    private String id;

    private String email;

    private String username;

    private List<Role> roles;

}
