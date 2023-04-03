package com.example.flickhunt.models

data class BookmarkMovieData(
    var movieId : Int,
    var imdbID        : String?            = null,
    var Title         : String?            = null,
    var Type          : String?            = null,
    var Poster        : String?            = null,
)
