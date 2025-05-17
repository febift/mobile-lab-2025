// RickApiService.java
package com.example.praktikum_6;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RickApiService {
    @GET("character")
    Call<ApiResponse> getCharacters(@Query("page") int page);

    @GET("character/{id}")
    Call<Post> getCharacterById(@Path("id") int id);
}
