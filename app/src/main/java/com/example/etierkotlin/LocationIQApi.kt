package com.example.etierkotlin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationIQApi {
    @GET("autocomplete.php")
    fun getAutocomplete(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("format") format: String = "json"
    ): Call<List<LocationIQPlace>>
}
