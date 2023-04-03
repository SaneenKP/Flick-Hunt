package com.example.flickhunt.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.flickhunt.models.Movie
import com.example.flickhunt.paging.MoviePagingSource
import com.example.flickhunt.utils.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Paging config class.
 */
class MoviePagingRepository @Inject constructor(
    private val movieRepository: MovieRepository
) {

    private var pagingConfig: PagingConfig

    init {
        pagingConfig =
            PagingConfig(pageSize = Constants.PAGE_SIZE, maxSize = Constants.MAX_PAGE_COUNT)
    }

    fun getMovies(
        searchQuery: String,
        movieType: String,
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                MoviePagingSource(
                    searchQuery,
                    movieType,
                    movieRepository
                )
            }).flow
    }
}