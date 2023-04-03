package com.example.flickhunt.repository

import com.example.flickhunt.models.Movie
import com.example.flickhunt.models.SearchResult
import com.example.flickhunt.retrofit.MovieNetworkClient
import com.example.flickhunt.retrofit.ResponseWrapper2
import com.example.flickhunt.utils.Constants
import java.io.IOException
import javax.inject.Inject

/***
 * Repository class to call Retrofit Api.
 */

class MovieRepository @Inject constructor(
    private val movieNetworkClient: MovieNetworkClient
) {

    //Searches movie and returns the result in a response wrapper.
    suspend fun searchMovie(
        searchQuery : String,
        page : Int,
        movieType : String
    ) : ResponseWrapper2<SearchResult?> {

        return try {
            val response = movieNetworkClient.searchMovie(searchQuery , page , movieType)
            if (response.isSuccessful) {
                handleSearchResponse(response.body())
            }else{
                ResponseWrapper2.Error(Constants.SOMETHING_WENT_WRONG_ERROR)
            }
        }catch (e : Exception){
            return ResponseWrapper2.Error(e.message.toString())
        }catch (e : IOException){
            return ResponseWrapper2.Error(e.message.toString())
        }
    }

    //gets a specific movie based on movie id and returns the result in a response wrapper.
    suspend fun getMovie(
        movieId : String
    ): ResponseWrapper2<Movie?>{

        return try {
            val response = movieNetworkClient.getMovie(movieId)
            if (response.isSuccessful) {
                handleMovieResponse(response.body())
            }else{
                ResponseWrapper2.Error(Constants.SOMETHING_WENT_WRONG_ERROR)
            }
        }catch (e : Exception){
            return ResponseWrapper2.Error(e.message.toString())
        }catch (e : IOException){
            return ResponseWrapper2.Error(e.message.toString())
        }
    }

    /**
     * handles Response of movies and returns a response wrapper.
     * This is done since even if there are no responses of the api call , the api would still return a success.
     * This method checks if the response variable is false in the body or not. If it is then it return the error.
     * else at the end if there are no errors and the response variable is true , it meas there is a valid data and the response wrapper with success.
     */
    private fun handleMovieResponse(body: Movie?): ResponseWrapper2<Movie?> {
        return if (body == null){
            ResponseWrapper2.Error(Constants.SOMETHING_WENT_WRONG_ERROR)
        }else if (body.Response == "False"){
            if (body.error == null) ResponseWrapper2.Error(Constants.SOMETHING_WENT_WRONG_ERROR)
            else ResponseWrapper2.Error(body.error!!)
        }else{
            ResponseWrapper2.Success(body)
        }
    }

    /**
     * handles Response of movies and returns a response wrapper.
     * This is done since even if there are no responses of the api call , the api would still return a success.
     * This method checks if the response variable is false in the body or not. If it is then it return the error.
     * else at the end if there are no errors and the response variable is true , it meas there is a valid data and the response wrapper with success.
     */
    private fun handleSearchResponse(body: SearchResult?) : ResponseWrapper2<SearchResult> {
        return if (body == null){
            ResponseWrapper2.Empty()
        }else if (body.Response == "False"){
            if (body.error == null) ResponseWrapper2.Error(Constants.SOMETHING_WENT_WRONG_ERROR)
            else ResponseWrapper2.Error(body.error!!)
        }else{
            ResponseWrapper2.Success(body)
        }
    }

}