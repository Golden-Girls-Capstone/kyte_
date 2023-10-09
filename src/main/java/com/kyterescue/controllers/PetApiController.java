package com.kyterescue.controllers;

import com.kyterescue.services.GrabApiDataService;
import com.kyterescue.services.GrabAuthenticationTokenService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        model.addAttribute("token", grabToken.getBearerToken());
        return grabData.findAllPetsByZipcode(78249);
    }

}
