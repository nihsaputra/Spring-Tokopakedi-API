package com.enigma.tokopakedi.model;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String email;
    private List<String> roles;

}
