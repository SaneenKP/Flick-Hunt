package com.example.flickhunt.utils
class Constants {

    companion object {
        const val connectionTimeOutSeconds : Long = 60;

        //Home
        const val DEFAULT_MOVIE_SEARCH_QUERY = "batman"
        const val INITIAL_PAGE_NUMBER = 1
        const val INITIAL_MOVIE_TYPE = ""
        const val COLUMN_COUNT = 3;
        const val IMAGE_BINDING_ADAPTER_VARIABLE = "imageFromPath"

        //DB
        const val DB_NAME  = "MovieSearchDB"
        const val DB_VERSION  = 1
        const val MOVIE_TABLE_NAME = "Movie"

        //Paging
        const val PAGE_SIZE = 10
        const val MAX_PAGE_COUNT = 100


        //Response
        enum class Status {
            SUCCESS,
            ERROR,
            LOADING,
            NETWORK_ERROR
        }

        sealed class ResponseStatus{
            object Success : ResponseStatus()
            object Error : ResponseStatus()
            object Loading : ResponseStatus()
            object Empty : ResponseStatus()
        }

        //Movie Types
        private const val TYPE_MOVIE = "movie"
        private const val TYPE_SERIES = "series"
        private const val TYPE_EPISODE = "episode"
        private const val TYPE_HOME = ""

        private const val TYPE_MOVIE_TEXT = "Movie"
        private const val TYPE_SERIES_TEXT = "Series"
        private const val TYPE_EPISODE_TEXT = "Episode"
        private const val TYPE_HOME_TEXT = "Home"

        val movieTypes = listOf<String>(TYPE_HOME, TYPE_MOVIE , TYPE_SERIES , TYPE_EPISODE)
        val movieTypeTexts = listOf<String>(
            TYPE_HOME_TEXT,
            TYPE_MOVIE_TEXT , TYPE_SERIES_TEXT , TYPE_EPISODE_TEXT
        )

        //API Query Params
        const val API_KEY_QUERY = "apikey"
        const val SEARCH_QUERY = "s"
        const val TYPE_QUERY = "type"
        const val PAGE_QUERY = "page"
        const val MOVIE_ID_QUERY = "i"

        //Bottom sheet states
        const val DRAGGING = "dragging"
        const val SETTLING = "settling"
        const val EXPANDING = "expanding"
        const val COLLAPSED = "collapsed"
        const val HIDDEN = "hidden"
        const val HALF_EXPANDED = "half expanded"

        //Ratings
        const val IMBD_RATING = "Internet Movie Database"
        const val ROTTEN_TOMATO_RATING = "Rotten Tomatoes"
        const val METACRITIC_RATING = "Metacritic"

        //Error responses
        const val MOVIE_NOT_FOUND = "Movie not found!"
        const val SOMETHING_WENT_WRONG_ERROR = "oops !! Something Went Wrong :( "
        const val NO_RESULT_ERROR = "Sorry mate !! No Results for your search"

        const val NOT_AVAILABLE = "N/A"
    }
}