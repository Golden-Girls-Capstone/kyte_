package com.kyterescue.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.*;
import com.kyterescue.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class PetApiController {

    GrabAuthenticationTokenService grabToken;
    UserRepository usersDao;
    PetRepository petsDao;
    FosterPetRepository fostersDao;
    GrabApiDataService grabData;
    PetMapperService mapperService;
    FavoritesService favoritesService;
    FosterPetValidationService fosterPetValidationService;
    DashboardFosterDisplayService dashboardFosterDisplayService;
    FullFosterPetValidationService fullFosterPetValidationService;


    PetApiController(
            GrabApiDataService grabData,
            FosterPetRepository fostersDao,
            UserRepository usersDao,
            GrabAuthenticationTokenService grabToken,
            PetRepository petsDao,
            PetMapperService mapperService,
            FavoritesService favoritesService,
            FosterPetValidationService fosterPetValidationService,
            DashboardFosterDisplayService dashboardFosterDisplayService,
            FullFosterPetValidationService fullFosterPetValidationService
    ){
        this.grabData = grabData;
        this.grabToken = grabToken;
        this.petsDao = petsDao;
        this.mapperService = mapperService;
        this.usersDao = usersDao;
        this.fostersDao = fostersDao;
        this.favoritesService = favoritesService;
        this.fosterPetValidationService = fosterPetValidationService;
        this.dashboardFosterDisplayService = dashboardFosterDisplayService;
        this.fullFosterPetValidationService = fullFosterPetValidationService;
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
            @ModelAttribute SearchForm searchForm
    ) throws IOException {
        searchForm.setPage(1);
        return grabData.findAnimalsBySearch(searchForm.getType(), searchForm.getAge(), searchForm.getSize(), searchForm.getZipcode(), searchForm.getPage());
    }
    @GetMapping(value = "api/data/types", produces = "application/json")
    public String apiCallTypes() throws IOException {
        return grabData.findAnimalTypes();
    }
    @PostMapping(value = "/browse/pet", produces = "application/json")
    public Pet retrieveAndMapPet(@RequestBody Pet apiPet) throws IOException{
        return mapperService.checkAndMapToPet(apiPet);
    }
    @PostMapping(value = "/browse/foster/{petId}/{startDate}/{endDate}", produces = "application/json")
    public FosterPet createFosterPet(@PathVariable long petId, @PathVariable String startDate, @PathVariable String endDate, @CurrentSecurityContext(expression = "authentication?.name") String username) throws JsonProcessingException {
        return fullFosterPetValidationService.run(usersDao.findByUsername(username), petsDao.getPetById(petId), startDate, endDate);
    }
    @PostMapping(value = "browse/favorite/{petId}")
    public void addFavorite(@PathVariable long petId, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        favoritesService.toggleFavorite(petsDao.getPetById(petId), usersDao.findByUsername(username), model);
    }
}
