package com.kyterescue.controllers;

import com.kyterescue.services.GrabApiDataService;
import com.kyterescue.services.GrabAuthenticationTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PetApiController {

    GrabAuthenticationTokenService grabToken;
    GrabApiDataService grabData;

    PetApiController(GrabApiDataService grabData, GrabAuthenticationTokenService grabToken) {
        this.grabData = grabData;
        this.grabToken = grabToken;
    }
    @GetMapping(value = "/api/data", produces = "application/json")
    public String apiCall(Model model) throws IOException {
        return grabData.findAllPetsByZipcode(78249);
    }

    @GetMapping(value = "api/token", produces = "text/plain")
    public ResponseEntity<String> getToken() throws IOException {
        String token = grabToken.getBearerToken();
        return ResponseEntity.ok(token);
    }

}
