package com.example.flickhunt.models

import com.google.gson.annotations.SerializedName

data class SearchResult (
    @SerializedName("Search"       ) var result       : ArrayList<Movie> = arrayListOf(),
    @SerializedName("totalResults" ) var totalResults : String?           = null,
    @SerializedName("Response"     ) var Response     : String?           = null,
    @SerializedName("Error"     )    var error        : String?           = null
)
