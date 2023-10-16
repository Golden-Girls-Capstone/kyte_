package com.kyterescue.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.TokenMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
@Service
public class GrabAuthenticationTokenService {
    @Value("${pet.token}")
    private String petToken;
    @Value("${pet.secret}")
    private String petSecret;
    public String tokenURL = "https://api.petfinder.com/v2/oauth2/token";
    private OkHttpClient client = new OkHttpClient();
    private ObjectMapper mapper = new ObjectMapper();

    GrabAuthenticationTokenService() {

    }

    public String getBearerToken() throws IOException {
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", petToken)
                .add("client_secret", petSecret)
                .build();
        Request request = new Request.Builder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .url(tokenURL)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            TokenMapper tokenMapper = mapper.readValue(response.body().string(), TokenMapper.class);
            return tokenMapper.getAccess_token();
        }
    }
}
