// ChatViewModel.java
package com.example.lmstudio;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<List<ChatMessage>> messages = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private ApiService apiService;
    private String host;

    public ChatViewModel() {
    }

    public void initialize(String backendUrl) {
        this.host = backendUrl;
        initializeRetrofit();
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

    public LiveData<List<ChatMessage>> getMessages() {
        return messages;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }



    public void sendMessage(String userMessage) {
        isLoading.setValue(true);
        List<ChatMessage> currentMessages = new ArrayList<>(messages.getValue());
        ChatMessage newMessage = new ChatMessage("user", userMessage);
        currentMessages.add(newMessage);
        messages.setValue(currentMessages);

        ChatRequest request = new ChatRequest(currentMessages);

        apiService.sendMessage(request).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().getChoices().isEmpty()) {
                    ChatMessage botMessage = response.body().getChoices().get(0).getMessage();
                    List<ChatMessage> updatedMessages = new ArrayList<>(messages.getValue());
                    updatedMessages.add(botMessage);
                    messages.postValue(updatedMessages);
                } else {
                    handleError("Error en la respuesta del servidor");
                }
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                handleError("Error: " + t);
                isLoading.postValue(false);
            }
        });
    }

    private void handleError(String errorMessage) {
        List<ChatMessage> currentMessages = new ArrayList<>(messages.getValue());
        currentMessages.add(new ChatMessage("assistant", errorMessage));
        messages.postValue(currentMessages);
    }
}