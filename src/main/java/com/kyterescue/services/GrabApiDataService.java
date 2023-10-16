package com.kyterescue.services;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

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

    public String findAnimalsBySearch(String type, String age, String size, int zipcode, int page) throws IOException {
        String results = "";
        Request request = new Request.Builder()
                .url(petURL + "type=" + type +  "&age=" + age + "&size=" + size + "&location=" + zipcode + "&page=" + page)
                .addHeader("Authorization", "Bearer " + grabAuthenticationTokenService.getBearerToken())
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("unexpected response code " + response);
            }
            results = response.body().string();
        } catch (IOException e) {
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
