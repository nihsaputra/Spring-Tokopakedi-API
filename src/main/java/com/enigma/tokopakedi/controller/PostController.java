package com.enigma.tokopakedi.controller;

import lombok.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(path = "/articles")
@RequiredArgsConstructor
public class PostController {

    private final RestTemplate restTemplate;

//    @GetMapping
//    private ResponseEntity<?> getAllPost(){
//        String url = "https://jsonplaceholder.typicode.com/posts";
//        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
//        return response;
//    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody Article article){
        HttpEntity<Article> request = new HttpEntity<>(article);
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles";
        return restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<>() {
        });
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles/"+ id;
        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestBody Article article){
        HttpEntity<Article> request = new HttpEntity<>(article);
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles";
        return restTemplate.exchange(url, HttpMethod.PUT, request, new ParameterizedTypeReference<>() {
        });
    }



    @GetMapping
    private ResponseEntity<?> getAllPost(){
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles";
        ResponseEntity<List<Article>> exchange = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        return exchange;
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ArticleResponse{
        private String id;
        private String title;
        private String body;
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Article{
        private String id;
        private String title;
        private String body;
        private String author;
    }


}
