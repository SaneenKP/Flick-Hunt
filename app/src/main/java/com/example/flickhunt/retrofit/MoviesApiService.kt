package com.example.flickhunt.retrofit

import com.example.flickhunt.models.Movie
import com.example.flickhunt.models.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET(".")
    suspend fun searchMovie(@Query("s") searchString : String , @Query("page") page : Int , @Query("type") type : String) : Response<SearchResult>

    @GET(".")
    suspend fun getMovie(@Query("i") movieId : String) : Response<Movie>

}