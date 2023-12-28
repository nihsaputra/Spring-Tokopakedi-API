package com.enigma.tokopakedi.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AuthRequest {
    private String email;
    private String password;
}
