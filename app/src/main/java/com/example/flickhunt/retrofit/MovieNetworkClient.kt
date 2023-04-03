package com.example.flickhunt.retrofit

import com.example.flickhunt.models.Movie
import com.example.flickhunt.models.SearchResult
import retrofit2.Response
import javax.inject.Inject

class MovieNetworkClient @Inject constructor(
    private val movieRetrofitService: MovieRetrofitService
) {
    suspend fun searchMovie(searchQuery : String , page : Int , movieType : String) : Response<SearchResult>{
        return movieRetrofitService.getMoviesApiService().searchMovie(searchQuery , page , movieType)
    }

    suspend fun getMovie(movieId : String) : Response<Movie>{
        return movieRetrofitService.getMoviesApiService().getMovie(movieId)
    }
}