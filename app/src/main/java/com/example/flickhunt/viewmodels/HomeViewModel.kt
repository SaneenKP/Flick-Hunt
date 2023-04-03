package com.example.flickhunt.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickhunt.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun getMovies(){
        viewModelScope.launch {
            val response = movieRepository.searchMovie("batman" , 1 , "")
            Log.d("saneen" , "response - ${response.data}")
        }
    }

}