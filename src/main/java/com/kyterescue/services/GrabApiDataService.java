//package com.kyterescue.services;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kyterescue.entities.Pet;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//
//@Service
//public class GrabApiDataService {
//    final OkHttpClient client = new OkHttpClient();
//    @Value("${pet.url}")
//    private String petURL;
//    List<Pet> petResults;
//    GrabAuthenticationTokenService grabAuthenticationTokenService;
//
//    GrabApiDataService(GrabAuthenticationTokenService grabAuthenticationTokenService) {
//        this.grabAuthenticationTokenService = grabAuthenticationTokenService;
//    }
//
//    public List<Pet> findAllPetsByZipcode(int zipcode) throws IOException {
//        Request request = new Request.Builder()
//                .url(petURL + "location=" + zipcode)
//                .addHeader("Authorization", grabAuthenticationTokenService.getBearerToken())
//                .build();
//        try(Response response = client.newCall(request).execute()) {
//                if(!response.isSuccessful()) {
//                    throw new IOException("Unexpected response code" + response);
//                }
//                String results = response.body().string();
//            ObjectMapper mapper = new ObjectMapper();
//            petResults = mapper.readValue(results, mapper.getTypeFactory().constructCollectionType(List.class, Pet.class));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return petResults;
//    }
//}
