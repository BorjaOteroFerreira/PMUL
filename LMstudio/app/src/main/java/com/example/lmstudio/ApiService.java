// ApiService.java
package com.example.lmstudio;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiService {
    @POST("/v1/chat/completions")
    Call<ChatResponse> sendMessage(@Body ChatRequest request);
}