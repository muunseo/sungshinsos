package com.example.sungshinsos;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DialogflowClient {

    private static final String TAG = "DialogflowClient";
    private static final String SERVER_URL = "http://10.0.2.2:3000/chatbot"; // 서버의 엔드포인트 URL로 변경
    private OkHttpClient httpClient;

    public DialogflowClient() {
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public String sendQuery(String query) {
        // 서버로 보낼 요청 본문 생성
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("message", query);

        // 서버에 요청 보내기
        Request request = new Request.Builder()
                .url(SERVER_URL)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(requestBody)))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Log.e(TAG, "Unexpected code " + response);
                return "Error: " + response.message();
            }
            // 응답을 파싱하여 결과 반환
            String responseBody = response.body().string();
            JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
            return jsonResponse.getAsJsonPrimitive("response").getAsString();
        } catch (IOException e) {
            Log.e(TAG, "Request failed", e);
            return "Error: " + e.getMessage();
        }
    }
}
