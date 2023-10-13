package com.kyterescue.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.SearchForm;
import com.kyterescue.services.GrabApiDataService;
import com.kyterescue.services.GrabAuthenticationTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class PetApiController {

    GrabAuthenticationTokenService grabToken;
    GrabApiDataService grabData;

    PetApiController(GrabApiDataService grabData, GrabAuthenticationTokenService grabToken) {
        this.grabData = grabData;
        this.grabToken = grabToken;
    }
    @GetMapping(value = "api/token", produces = "text/plain")
    public ResponseEntity<String> getToken() throws IOException {
        String token = grabToken.getBearerToken();
        return ResponseEntity.ok(token);
    }
    @GetMapping(value = "/api/data/default", produces = "application/json")
    public String apiCallDefault() throws IOException {
        return grabData.findAnimalsBySearch("cat", "baby", "small", 78249, 1);
    }
    @GetMapping(value = "/api/data/search", produces = "application/json")
    public String apiCallSearch(
            @ModelAttribute SearchForm searchForm) throws IOException {
        searchForm.setPage(1);
        return grabData.findAnimalsBySearch(searchForm.getType(), searchForm.getAge(), searchForm.getSize(), searchForm.getZipcode(), searchForm.getPage());
    }
    @GetMapping(value = "api/data/types", produces = "application/json")
    public String apiCallTypes() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(grabData.findAnimalTypes()));
        return grabData.findAnimalTypes();
    }

}
