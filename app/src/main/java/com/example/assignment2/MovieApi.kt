package com.example.assignment2

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

//@Dao
//interface MovieDao {
//
//}

//@Entity(tableName = "MovieRes")
//    @PrimaryKey
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
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_count") val totalCount: Int
)

private const val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NGJhMzM1MWMwNTNiMzE0NmZjMGExZDRmMDY0MTUxMCIsIm5iZiI6MTczNDc3NTU3Mi45NDcsInN1YiI6IjY3NjY5MzE0OTE5Mjg3ZWY1MzkwZmExNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.nqCZPNxecZbRimaChDwJpMMX4I-0F4UGbGy6bh1Uhic"

interface MovieApiService {
    @GET("{endpoint}")
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer $token" // Replace with your actual token
    )
    suspend fun getMovies(
        @Path("endpoint") endpoint: String = RequestUrl.POPULAR.getEndpoint(),
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse?
}

private const val BASE_URL = "https://api.themoviedb.org/"

val okHttpClient = OkHttpClient.Builder()
    .build()

// Retrofit instance
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

// API service
object MovieApi {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}