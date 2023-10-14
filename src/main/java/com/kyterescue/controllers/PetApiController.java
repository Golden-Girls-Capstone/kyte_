package com.kyterescue.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.*;
import com.kyterescue.services.GrabApiDataService;
import com.kyterescue.services.GrabAuthenticationTokenService;
import com.kyterescue.services.PetMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class PetApiController {

    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");

    GrabAuthenticationTokenService grabToken;
    UserRepository usersDao;
    PetRepository petsDao;
    FosterPetRepository fostersDao;
    GrabApiDataService grabData;
    PetMapperService mapperService;

    PetApiController(GrabApiDataService grabData, FosterPetRepository fostersDao, UserRepository usersDao, GrabAuthenticationTokenService grabToken, PetRepository petsDao, PetMapperService mapperService) {
        this.grabData = grabData;
        this.grabToken = grabToken;
        this.petsDao = petsDao;
        this.mapperService = mapperService;
        this.usersDao = usersDao;
        this.fostersDao = fostersDao;
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
    @PostMapping(value = "/browse/pet", produces = "application/json")
    public Pet retrieveAndMapPet(@RequestBody Pet apiPet) throws IOException{
        return mapperService.checkAndMapToPet(apiPet);
    }
    @PostMapping(value = "/browse/foster/{petId}/{startDate}/{endDate}", produces = "application/json")
    public FosterPet createFosterPet(@PathVariable long petId, @PathVariable String startDate, @PathVariable String endDate, @CurrentSecurityContext(expression = "authentication?.name") String username) throws JsonProcessingException {
        LocalDate localStart = LocalDate.of(Integer.parseInt(startDate.substring(0, 4)), Integer.parseInt(startDate.substring(5, 7)), Integer.parseInt(startDate.substring(8, 10)));
        LocalDate localEnd = LocalDate.of(Integer.parseInt(endDate.substring(0, 4)), Integer.parseInt(endDate.substring(5, 7)), Integer.parseInt(endDate.substring(8, 10)));
        FosterPet foster = new FosterPet(localStart, localEnd, usersDao.findByUsername(username), petsDao.getPetById(petId), true);
        fostersDao.save(foster);
        return foster;
    }
}
