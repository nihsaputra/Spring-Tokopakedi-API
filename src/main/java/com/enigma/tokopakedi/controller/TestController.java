package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.UserCredential;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class    TestController {

    @GetMapping(path = "/test")
    public String getId(){
        UserCredential principal =(UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }

}
