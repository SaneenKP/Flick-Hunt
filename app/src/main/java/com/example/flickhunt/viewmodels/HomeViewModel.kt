package com.example.flickhunt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.flickhunt.repository.MoviePagingRepository
import com.example.flickhunt.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val pagingRepository: MoviePagingRepository
) : ViewModel() {

    private val movieTypeFlow = MutableStateFlow("")
    private val movieSearchFlow = MutableStateFlow("batman")

    private val movieTypeAndSearchCombinedFlow: Flow<Pair<String, String>> =
        movieTypeFlow.combine(movieSearchFlow) { type, search ->
            Pair(type, search)
        }

    var movieList = movieTypeAndSearchCombinedFlow.flatMapLatest { (type, search) ->
        pagingRepository.getMovies(search, type).cachedIn(viewModelScope)
    }

    fun onMovieTypeSwitched(movieTypeText: String) {
        viewModelScope.launch {
            movieTypeFlow.emit(movieTypeText)
        }
    }

}