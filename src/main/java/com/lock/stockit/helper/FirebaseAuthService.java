package com.lock.stockit.helper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FirebaseAuthService {

    private static final String API_KEY = "AIzaSyBb9Upzy1TCZRtOltS6nVIbPPCXDuuDRwU";

    public static JsonObject signIn(String email, String password) throws IOException {
        String endpoint = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY;

        JsonObject body = new JsonObject();
        body.addProperty("email", email);
        body.addProperty("password", password);
        body.addProperty("returnSecureToken", true);

        URL url = URI.create(endpoint).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.toString().getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        InputStream stream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            JsonObject json = new Gson().fromJson(reader, JsonObject.class);

            if (responseCode == 200) {
                return json;
            } else {
                throw new IOException(json.getAsJsonObject("error").get("message").getAsString());
            }
        }
    }
}