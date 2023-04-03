package com.example.flickhunt.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.flickhunt.repository.MoviePagingRepository
import com.example.flickhunt.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.m
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val pagingRepository: MoviePagingRepository
) : ViewModel() {

    private var movieType = MutableStateFlow("")

    var movieList = movieType.flatMapLatest {type ->
        pagingRepository.getMovies("batman" , type).cachedIn(viewModelScope)
    }

    fun onMovieTypeSwitched(movieTypeText : String){
        viewModelScope.launch {
            movieType.emit(movieTypeText)
        }
    }

}