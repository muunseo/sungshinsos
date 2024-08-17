package com.example.sungshinsos;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DialogflowClient {

    private static final String TAG = "DialogflowClient";
    private static final String API_URL = "https://dialogflow.googleapis.com/v2/projects/%s/agent/sessions/%s:detectIntent";
    private static final String BEARER_TOKEN = "fzUx1yMDQ-W8hPgdXJES4c:APA91bGuInDqArlg7-EEYVuvzkZrvZJ6hZGBRIFL4EZaQYxwRbCrK50oN-rlTYXrEbX6uVs-8N9J9OiwxHObbyNNG5B37V8cMJe_6KyQ37qanv4ZiU1CFaSyZkb1dG3HX7p9d9hfmb_K"; // Google Cloud Access Token

    private OkHttpClient httpClient;

    public DialogflowClient() {
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public String sendQuery(String projectId, String sessionId, String query) {
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
                .addHeader("Authorization", "Bearer " + BEARER_TOKEN)
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
