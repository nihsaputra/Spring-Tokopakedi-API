package com.enigma.tokopakedi.model;

import com.enigma.tokopakedi.entity.UserCredential;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private String id;

    private String name;

    private String address;

    private String phoneNumber;

    private UserCredentialResponse userCredential;

}
