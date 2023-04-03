package com.example.flickhunt.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.flickhunt.repository.MoviePagingRepository
import com.example.flickhunt.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val pagingRepository: MoviePagingRepository
) : ViewModel() {

    val movieList = pagingRepository.getMovies("batman" , "").cachedIn(viewModelScope)

}