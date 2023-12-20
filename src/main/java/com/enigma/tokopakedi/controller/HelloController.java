package com.enigma.tokopakedi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    private List<Map<String,Object>> users= List.of(
            Map.of( "id",1,"name","Saputra"),
            Map.of("id",2,"name","Khotim")
    );

    private List<String> fruits= List.of("Apple" , "Mangga" , "Mangga Muda", "Mangga Busuk", "Nanas", "Nanas Muda", "Nanas Busuk");

    @GetMapping(path = "/")
    public String helloWorld(){
        return "<h1>Hello World</h1>";
    }

    @GetMapping(path = "/buah")
    public List<String> fruits(){
        return List.of("Apel","Melon","Semangka","Jeruk");
    }

    @GetMapping(path = "/cars")
    public Map<String,Object> cars(){
        return Map.of(
                "brand","Toyota",
                "name","Supra",
                "year",2020
        );
    }

    @GetMapping(path = "/users/{userId}")
    public Map<String, Object> getUserById(@PathVariable Integer userId){
        Map<String, Object> tempt= new HashMap<>();

        for (Map<String, Object> user : users) {
            if (user.get("id").equals(userId)){
                tempt=user;
            }
        }
        return tempt;
    }


    @GetMapping(path = "/fruits")
    //localhost:8081/fruits?search=mangga
    public List<String> getList(@RequestParam String search){
        List<String> result = fruits.stream().filter(s -> s.toLowerCase()
                .contains(search.toLowerCase()))
                .toList();
        return result;
    }

    @PostMapping(path = "/users")
    public User postUser(@RequestBody User user){
        return user;
    }
}
