package com.kyterescue.controllers;

import com.kyterescue.services.GrabApiDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PetApiController {

    GrabApiDataService grabData;

    PetApiController(GrabApiDataService grabData) {
        this.grabData = grabData;
    }
    @GetMapping(value = "/api/data", produces = "application/json")
    public String apiCall() throws IOException {
        return grabData.findAllPetsByZipcode(78249);
    }

}
