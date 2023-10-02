package com.kyterescue.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PetApiService {
    @Value("${pet.token}")
    private String petToken;
    @Value("${pet.url}")
    private String petURL;
    private RestTemplate restTemplate;

    @Autowired
    public PetApiService(RestTemplate restTemplate, @Value("${pet.token}") String petToken, @Value("${pet.url}") String petURL) {
        this.restTemplate = restTemplate;
        this.petToken = petToken;
        this.petURL = petURL;
    }


}
