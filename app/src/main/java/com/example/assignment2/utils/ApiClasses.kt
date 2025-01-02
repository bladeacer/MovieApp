package com.example.assignment2.utils

import com.google.gson.annotations.SerializedName

enum class RequestUrl(val url: String) {
    POPULAR("3/movie/popular"),
    TOP_RATED("3/movie/top_rated"),
    UPCOMING("3/movie/upcoming"),
    NOW_PLAYING("3/movie/now_playing");

    fun getEndpoint(): String = url
}

data class SearchCriteria(
    val query: String,
    val pageNumber: Int = 1
)

data class IdSearchCriteria(
    val id: Int,
    val pageNumber: Int = 1
)

data class MovieResponse(
    val page: Int = 1,
    val results: List<Movie> = listOf<Movie>(),
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0
)

data class Movie(
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)

data class Genre(
    val id: Int = 0,
    val name: String
)

data class ProductionCompany(
    val id: Int = 0,
    val logoPath: String,
    @SerializedName("name") val name: String,
    @SerializedName("origin_country") val originCountry: String
)

data class ProductionCountry(
    @SerializedName("iso_3166_1") val iso31661: String,
    val name: String
)

data class SpokenLanguage(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso6391: String,
    val name: String
)

data class MovieDetail(
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("belongs_to_collection") val belongsToCollection: String?,
    val budget: Int,
    val genres: List<Genre>?,
    val homepage: String,
    val id: Int = 0,
    @SerializedName("imdb_id") val imdbID: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double = 0.0,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
    @SerializedName("release_date") val releaseDate: String?,
    val revenue: Int = 0,
    val runtime: Int = 0,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    val status: String,
    val tagline: String,
    val title: String?,
    val video: Boolean = true,
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0
)

data class AuthorDetail(
    val name: String,
    val username: String,
    val avatarPath: String,
    val rating: String
)

data class ReviewResult(
    val author: String,
    @SerializedName("author_details") val authorDetails: List<AuthorDetail>,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)

data class Review(
    val id: Int = 0,
    val page: Int = 0,
    val results: List<ReviewResult>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)


data class SearchEntry(
    val id: Int = 0,
    val name: String
)

data class Search(
    val page: Int = 0,
    val results: List<SearchEntry>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
