package com.example.sungshinsos;

import android.content.Context;
import android.util.Log;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class DialogflowClient {

    private static final String TAG = "DialogflowClient";
    private static final String API_URL = "https://dialogflow.googleapis.com/v2/projects/%s/agent/sessions/%s:detectIntent";

    private OkHttpClient httpClient;

    public DialogflowClient(Context context) {
        try {
            // JSON 키 파일을 assets에서 읽기
            InputStream credentialsStream = context.getAssets().open("sungshinsos-catbot.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                    .createScoped("https://www.googleapis.com/auth/cloud-platform");

            httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            // Access token 갱신 로직 추가
                            String accessToken = getAccessToken(credentials);
                            return response.request().newBuilder()
                                    .header("Authorization", "Bearer " + accessToken)
                                    .build();
                        }
                    })
                    .build();
        } catch (IOException e) {
            Log.e(TAG, "Failed to initialize DialogflowClient", e);
        }
    }

    private String getAccessToken(GoogleCredentials credentials) throws IOException {
        // GoogleCredentials 객체를 사용하여 액세스 토큰을 얻는 로직
        credentials.refreshIfExpired();
        return credentials.getAccessToken().getTokenValue();
    }

    public String sendQuery(String projectId, String sessionId, String query) {
        if (httpClient == null) {
            Log.e(TAG, "HttpClient not initialized");
            return "Error: HttpClient not initialized";
        }

        String url = String.format(API_URL, projectId, sessionId);

        // Build request body
        JsonObject textInput = new JsonObject();
        textInput.addProperty("text", query);
        textInput.addProperty("languageCode", "ko");

        JsonObject queryInput = new JsonObject();
        queryInput.add("text", textInput);

        JsonObject requestBody = new JsonObject();
        requestBody.add("queryInput", queryInput);

        // Make the HTTP request
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(requestBody)))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Log.e(TAG, "Unexpected code " + response);
                return "Error: " + response.message();
            }
            // Parse the response
            String responseBody = response.body().string();
            JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
            JsonObject queryResult = jsonResponse.getAsJsonObject("queryResult");
            return queryResult.getAsJsonPrimitive("fulfillmentText").getAsString();
        } catch (IOException e) {
            Log.e(TAG, "Request failed", e);
            return "Error: " + e.getMessage();
        }
    }
}
