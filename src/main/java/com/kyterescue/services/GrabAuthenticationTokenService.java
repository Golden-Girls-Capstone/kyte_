//package com.kyterescue.services;
//
//import okhttp3.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//@Service
//public class GrabAuthenticationTokenService {
//    @Value("${pet.token}")
//    private String petToken;
//    @Value("${pet.secret}")
//    private String petSecret;
//    public String tokenURL = "https://api.petfinder.com/v2/oath2/token";
//    final OkHttpClient client = new OkHttpClient();
//
//    GrabAuthenticationTokenService(@Value("${pet.token}") String petToken, @Value("${pet.secret}") String petSecret) {
//        this.petToken = petToken;
//        this.petSecret = petSecret;
//    }
//
//   public String getBearerToken() throws IOException {
//        RequestBody body = new FormBody.Builder()
//                .add("token", "grant_type=client_credentials&client_id=" + petToken + "&client_secret=" + petSecret)
//                .build();
//        Request request = new Request.Builder()
//                .url(tokenURL)
//                .post(body)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
//    }
//}
