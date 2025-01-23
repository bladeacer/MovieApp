package com.example.assignment2

import com.example.assignment2.utils.MovieDetail
import com.example.assignment2.utils.MovieKeywords
import com.example.assignment2.utils.MovieResponse
import com.example.assignment2.utils.RequestUrl
import com.example.assignment2.utils.Review
import com.example.assignment2.utils.Search
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

private const val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NGJhMzM1MWMwNTNiMzE0NmZjMGExZDRmMDY0MTUxMCIsIm5iZiI6MTczNDc3NTU3Mi45NDcsInN1YiI6IjY3NjY5MzE0OTE5Mjg3ZWY1MzkwZmExNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.nqCZPNxecZbRimaChDwJpMMX4I-0F4UGbGy6bh1Uhic"
private const val BASE_URL = "https://api.themoviedb.org/"

interface MovieApiService {
    @GET("{endpoint}")
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer $token"
    )
    suspend fun getMovies(
        @Path("endpoint") endpoint: String = RequestUrl.POPULAR.getEndpoint(),
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse?

    @GET("3/movie/{movie_id}")
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer $token"
    )
    suspend fun getDetailById(
        @Path("movie_id") movieId: Int = 1,
        @Query("language") language: String = "en-US"
    ): MovieDetail?

    @GET("3/movie/{movie_id}/reviews")
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer $token"
    )
    suspend fun getReviewsById(
        @Path("movie_id") movieId: Int = 1,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Review?

    @GET("3/search/movie")
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer $token"
    )
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): MovieResponse?

    @GET("3/movie/{movie_id}/similar")
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer $token"
    )
    suspend fun getSimilar(
        @Path("movie_id") movieId: Int = 1,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse?


    @GET("3/movie/{movie_id}/keywords")
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer $token"
    )
    suspend fun getKeyword(
        @Path("movie_id") movieId: Int = 1,
    ): MovieKeywords?
}

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