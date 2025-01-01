package com.example.assignment2

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

import kotlin.random.Random

enum class RequestUrl(val url: String) {
    POPULAR("3/movie/popular"),
    TOP_RATED("3/movie/top_rated"),
    UPCOMING("3/movie/upcoming"),
    NOW_PLAYING("3/movie/now_playing");

    fun getEndpoint(): String = url
}

data class SearchCriteria(
    val query: String,
    val language: String,
    val pageNumber: Int
)

suspend fun delayRandomMillis(minMillis: Long = 0, maxMillis: Long = 1000) {
    val randomDelay = Random.nextLong(minMillis, maxMillis + 1)
    delay(randomDelay)
}

class MyViewModel : ViewModel() {
    private val _searchCriteria = MutableStateFlow(SearchCriteria(RequestUrl.POPULAR.url, "en-US", 1)) // Use 'by' for delegation
    val searchCriteria: StateFlow<SearchCriteria> = _searchCriteria.asStateFlow()

    private val _movieResponse = MutableStateFlow<MovieResponse?>(null)
    val movieResponse: StateFlow<MovieResponse?> = _movieResponse.asStateFlow()

//    init {
//        reset()
//    }

    fun fetchMovies() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                delayRandomMillis(1000, 2000)
                val result = withContext(Dispatchers.IO) {
                    try {
                        MovieApi.retrofitService.getMovies(searchCriteria.value.query,
                            searchCriteria.value.language, searchCriteria.value.pageNumber)
                    } catch (e: Exception) {
                        Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                        null
                    }
                }
//            snapshotFlow { searchCriteria.value }
//                .collect { criteria ->
//                    val call = fetchMovies(criteria.query, criteria.language, criteria.pageNumber)
//                }
                withContext(Dispatchers.Main) {
                    _movieResponse.value = result
                }
            }
        }
    }
    fun updateUrl(newUrl: String) {
        _searchCriteria.value = _searchCriteria.value.copy(query = newUrl)
    }
    fun updateLanguage(newLanguage: String) {
        _searchCriteria.value = _searchCriteria.value.copy(language = newLanguage)
    }
    fun updatePageNumber(newPageNumber: String) {
        val newPN = newPageNumber.filter { it.isDigit()}
        if (newPN.isEmpty() || newPN.toInt() < 1) {
            _searchCriteria.value = _searchCriteria.value.copy(pageNumber = 1)
        }
        else {
            _searchCriteria.value = _searchCriteria.value.copy(pageNumber = newPN.toInt())
        }
    }

}

