// ChatViewModel.java
package com.example.lmstudio;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<List<ChatMessage>> messages = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> completionTokensText = new MutableLiveData<>("");
    private ApiService apiService;
    private String host;

    public ChatViewModel() {
    }

    public void initialize(String backendUrl) {
        this.host = backendUrl;
        initializeRetrofit();
    }

    public LiveData<String> getCompletionTokensText() {
        return completionTokensText;
    }

    public LiveData<List<ChatMessage>> getMessages() {
        return messages;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void initializeRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(130, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void sendMessage(String userMessage) {
        isLoading.setValue(true);
        List<ChatMessage> currentMessages = new ArrayList<>(messages.getValue());
        // El mensaje del usuario no tiene tokens de completion
        currentMessages.add(new ChatMessage("user", userMessage, ""));
        messages.setValue(currentMessages);

        ChatRequest request = new ChatRequest(currentMessages);
        apiService.sendMessage(request).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        ChatResponse chatResponse = response.body();
                        if (chatResponse != null) {
                            List<ChatResponse.Choice> choices = chatResponse.getChoices();
                            if (choices != null && !choices.isEmpty()) {
                                ChatMessage botMessage = choices.get(0).getMessage();
                                if (botMessage != null) {
                                    String completionTokens = "";
                                    if (chatResponse.getUsage() != null) {
                                        completionTokens = String.format("Tokens: %s",
                                                chatResponse.getUsage().getCompletionTokens());
                                    }

                                    // Crear el mensaje del bot con los tokens
                                    ChatMessage messageWithTokens = new ChatMessage(
                                            botMessage.getRole(),
                                            botMessage.getContent(),
                                            completionTokens
                                    );

                                    List<ChatMessage> updatedMessages = new ArrayList<>(messages.getValue());
                                    updatedMessages.add(messageWithTokens);
                                    messages.postValue(updatedMessages);
                                } else {
                                    handleError("Mensaje del bot es nulo");
                                }
                            } else {
                                handleError("No hay choices disponibles en la respuesta");
                            }
                        } else {
                            handleError("Respuesta del servidor es nula");
                        }
                    } else {
                        handleError("Error en la respuesta del servidor: " +
                                response.code() + " - " + response.message());
                    }
                } catch (Exception e) {
                    handleError("Error procesando la respuesta: " + e.getMessage());
                } finally {
                    isLoading.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                handleError("Error de red: " + t.getMessage());
                isLoading.postValue(false);
            }
        });
    }
    private void handleError(String errorMessage) {
        List<ChatMessage> currentMessages = new ArrayList<>(messages.getValue());
        currentMessages.add(new ChatMessage("assistant", errorMessage, "0"));
        messages.postValue(currentMessages);
    }
}