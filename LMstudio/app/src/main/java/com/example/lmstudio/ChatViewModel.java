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
        if (userMessage == null || userMessage.trim().isEmpty()) {
            addErrorMessage("El mensaje del usuario está vacío.");
            return;
        }

        isLoading.setValue(true);
        appendUserMessage(userMessage);
        sendRequestToApi(new ChatRequest(new ArrayList<>(messages.getValue())));
    }

    private void appendUserMessage(String userMessage) {
        List<ChatMessage> currentMessages = new ArrayList<>(messages.getValue());
        currentMessages.add(new ChatMessage("user", userMessage, ""));
        messages.setValue(currentMessages);
    }

    private void sendRequestToApi(ChatRequest request) {
        apiService.sendMessage(request).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleSuccessfulResponse(response.body());
                } else {
                    handleError("Error en la respuesta del servidor: " +
                            response.code() + " - " + response.message());
                }
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                handleError("Error de red: " + t.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    private void handleSuccessfulResponse(ChatResponse chatResponse) {
        ChatMessage botMessage = extractBotMessage(chatResponse);
        if (botMessage != null) {
            appendBotMessage(botMessage, generateCompletionTokensText(chatResponse));
        } else {
            handleError("Mensaje del bot es nulo.");
        }
    }

    private ChatMessage extractBotMessage(ChatResponse chatResponse) {
        List<ChatResponse.Choice> choices = chatResponse.getChoices();
        if (choices != null && !choices.isEmpty()) {
            return choices.get(0).getMessage();
        }
        handleError("No hay choices disponibles en la respuesta.");
        return null;
    }

    private String generateCompletionTokensText(ChatResponse chatResponse) {
        if (chatResponse.getUsage() != null) {
            return "Respuesta: " + chatResponse.getUsage().getCompletionTokens() +
                    " tokens - Total: " + chatResponse.getUsage().getTotalTokens() + " tokens";
        }
        return "Tokens - Información no disponible";
    }

    private void appendBotMessage(ChatMessage botMessage, String completionTokens) {
        List<ChatMessage> updatedMessages = new ArrayList<>(messages.getValue());
        updatedMessages.add(new ChatMessage(
                botMessage.getRole(),
                botMessage.getContent(),
                completionTokens
        ));
        messages.postValue(updatedMessages);
    }

    private void handleError(String errorMessage) {
        addErrorMessage(errorMessage);
    }

    private void addErrorMessage(String errorMessage) {
        List<ChatMessage> currentMessages = new ArrayList<>(messages.getValue());
        currentMessages.add(new ChatMessage("assistant", errorMessage, "0"));
        messages.postValue(currentMessages);
    }
}
