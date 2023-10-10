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
        return grabData.findAllPetsByZipcode(78249);
    }
    @GetMapping(value = "/api/data/search", produces = "application/json")
    public String apiCallSearch(@RequestParam(name = "category", value = "category") String category, @RequestParam(name = "zipcode", value = "zipcode") int zipcode) throws IOException {
        SearchForm searchForm = new SearchForm();
        searchForm.setCategory(category);
        searchForm.setZipcode(zipcode);
        System.out.println(category);
        System.out.println(zipcode);
        if(searchForm.getZipcode() != 0 && searchForm.getCategory() != null) {
            return grabData.findAllPetsByZipcodeAndType(searchForm.getZipcode(), searchForm.getCategory());
        } else if(searchForm.getZipcode() == 0 && searchForm.getCategory() != null) {
            return grabData.findAllPetsByType(searchForm.getCategory());
        } else if(searchForm.getZipcode() !=0 && searchForm.getCategory() == null) {
            return grabData.findAllPetsByZipcode(searchForm.getZipcode());
        }
        return grabData.findAllPetsByZipcode(78249);
    }
    @GetMapping(value = "api/data/types", produces = "application/json")
    public String apiCallTypes() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(grabData.findAnimalTypes()));
        return grabData.findAnimalTypes();
    }

}
