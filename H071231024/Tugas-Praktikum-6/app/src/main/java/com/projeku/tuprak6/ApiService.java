package com.projeku.tuprak6;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/character")
    Call<CharacterResponse> getCharacters(@Query("page") int page);
}