package com.example.tentoclock;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class CalendlyAPI {
    private static final String API_URL = "https://api.calendly.com/scheduled_events";
    private static final String API_KEY = "eyJraWQiOiIxY2UxZTEzNjE3ZGNmNzY2YjNjZWJjY2Y" +
            "4ZGM1YmFmYThhNjVlNjg0MDIzZjdjMzJiZTgzNDliMjM4MDEzNWI0IiwidHlwIjoiUEFUIiwi" +
            "YWxnIjoiRVMyNTYifQ.eyJpc3MiOiJodHRwczovL2F1dGguY2FsZW5kbHkuY29tIiwiaWF0Ij" +
            "oxNzM5OTk0MDc1LCJqdGkiOiIzZDdmNTI1ZC1lNzMxLTQ0M2EtYTUwYS04ZjIzMTFhZTc0YmE" +
            "iLCJ1c2VyX3V1aWQiOiJiMmI4OGI5My01ZTgzLTRjMmItOWMxNy00ZTk3YjhkOTc4MDAifQ.c" +
            "ws-bMfy7Npd9kMqeqDCHSzmfsTm2QvYWiijcykzECBtOlYGypEuEa5792p4gamOzTrUhwdnjwv3RZIC0qLFmw";

    public static void scheduleEvent(String eventType, String startTime, String email, String name) {
        OkHttpClient client = new OkHttpClient();

        try {
            JSONObject invitee = new JSONObject();
            invitee.put("email", email);
            invitee.put("name", name);

            JSONObject requestBody = new JSONObject();
            requestBody.put("event_type", eventType);
            requestBody.put("start_time", startTime);
            requestBody.put("invitee", invitee);

            RequestBody body = RequestBody.create(requestBody.toString(), MediaType.parse("application/json"));

            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("Failed to schedule event: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        System.out.println("Event scheduled successfully: " + response.body().string());
                    } else {
                        System.out.println("Error scheduling event: " + response.body().string());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

