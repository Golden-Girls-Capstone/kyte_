package com.kyterescue.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.Pet;
import jakarta.persistence.GenerationType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GrabApiDataService {
    final OkHttpClient client = new OkHttpClient();
    @Value("${pet.url}")
            private String petURL;
    @Value("${single.pet.url}")
            private String singlePetUrl;
    GrabAuthenticationTokenService grabAuthenticationTokenService;
    GrabApiDataService(GrabAuthenticationTokenService grabAuthenticationTokenService) {
        this.grabAuthenticationTokenService = grabAuthenticationTokenService;
    }
    public String findAllPetsByZipcode(int zipcode) throws IOException {
        String results = "";
        Request request = new Request.Builder()
                .url(petURL + "location=" + zipcode)
                .addHeader("Authorization", "Bearer " + grabAuthenticationTokenService.getBearerToken())
                .build();
        try(Response response = client.newCall(request).execute()) {
                if(!response.isSuccessful()) {
                    throw new IOException("Unexpected response code " + response);
                }
                results = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return results;
    }
    public String findAllPetsByZipcodeAndType(int zipcode, String type) throws IOException {
        String results = "";
        Request request = new Request.Builder()
                .url(petURL + "location=" + zipcode + "&type=" + type)
                .addHeader("Authorization", "Bearer " + grabAuthenticationTokenService.getBearerToken())
                .build();
        System.out.println(petURL+"location="+zipcode+"&type=" + type);
        try(Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                throw new IOException("Unexpected response code " + response);
            }
            results = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
    public String findAllPetsByType(String type) throws IOException {
        String results = "";
        Request request = new Request.Builder()
                .url(petURL + "type=" + type)
                .addHeader("Authorization", "Bearer " + grabAuthenticationTokenService.getBearerToken())
                .build();
        try(Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                throw new IOException("Unexpected response code " + response);
            }
            results = response.body().string();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return results;
    }
    public String findAnimalById(long id) throws IOException {
        String results = "";
        Request request = new Request.Builder()
                .url(singlePetUrl + id)
                .addHeader("Authorization", "Bearer " + grabAuthenticationTokenService.getBearerToken())
                .build();
        try(Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                throw new IOException("Unexpected response code " + response);
            }
            results = response.body().string();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return results;
    }
    public String findAnimalTypes() throws IOException {
        String results = "";
        Request request = new Request.Builder()
                .url("https://api.petfinder.com/v2/types")
                .addHeader("Authorization", "Bearer " + grabAuthenticationTokenService.getBearerToken())
                .build();
        try(Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                throw new IOException("Unexpected response code " + response);
            }
            results = response.body().string();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
