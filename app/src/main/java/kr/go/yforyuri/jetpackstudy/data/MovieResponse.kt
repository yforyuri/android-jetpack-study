package com.example.paging3.network


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Search")
    val search: List<Movie> = listOf(),
    @SerializedName("totalResults")
    val totalResults: String = "", // 67
    @SerializedName("Response")
    val response: String = "" // True
)
data class Movie(
    @SerializedName("Title")
    val title: String = "", // Lucifer
    @SerializedName("Year")
    val year: String = "", // 2016–2021
    @SerializedName("imdbID")
    val imdbID: String = "", // tt4052886
    @SerializedName("Type")
    val type: String = "", // series
    @SerializedName("Poster")
    val poster: String = "" // https://m.media-amazon.com/images/M/MV5BNDJjMzc4NGYtZmFmNS00YWY3LThjMzQtYzJlNGFkZGRiOWI1XkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg
)