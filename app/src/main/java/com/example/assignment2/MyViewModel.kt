package com.example.assignment2

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.utils.MovieDetail
import com.example.assignment2.utils.MovieResponse
import com.example.assignment2.utils.RequestUrl
import com.example.assignment2.utils.Review
import com.example.assignment2.utils.Search
import com.example.assignment2.utils.SearchCriteria
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

import kotlin.random.Random


suspend fun delayRandomMillis(minMillis: Long = 0, maxMillis: Long = 1000) {
    val randomDelay = Random.nextLong(minMillis, maxMillis + 1)
    delay(randomDelay)
}

// TODO: For each search entry call the MovieDetail using the ID
class MyViewModel : ViewModel() {
    private val _searchCriteria = MutableStateFlow(SearchCriteria(RequestUrl.POPULAR.url, 1))
    val searchCriteria: StateFlow<SearchCriteria> = _searchCriteria.asStateFlow()

    private val _movieResponse = MutableStateFlow<MovieResponse?>(null)
    val movieResponse: StateFlow<MovieResponse?> = _movieResponse.asStateFlow()

    private val _similarResponse = MutableStateFlow<MovieResponse?>(null)
    val similarResponse: StateFlow<MovieResponse?> = _similarResponse.asStateFlow()

    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?> = _movieDetail.asStateFlow()

    private val _review = MutableStateFlow<Review?>(null)
    val review: StateFlow<Review?> = _review.asStateFlow()

    private val _keyword = MutableStateFlow<Search?>(null)
    val keyword: StateFlow<Search?> = _keyword.asStateFlow()

    private val _movieId = MutableStateFlow<Int>(1)
    val movieId: StateFlow<Int> = _movieId.asStateFlow()

    private val _reviewPageNo = MutableStateFlow<Int>(1)
    val reviewPageNo: StateFlow<Int> = _reviewPageNo.asStateFlow()

//    private val _uiState = MutableStateFlow<UiState<Any>>(UiState.Loading)
//    val uiState: StateFlow<UiState<Any>> = _uiState.asStateFlow()

    fun fetchMovies() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                delayRandomMillis()
                val result = withContext(Dispatchers.IO) {
                    try {
                        MovieApi.retrofitService.getMovies(searchCriteria.value.query, page = searchCriteria.value.pageNumber)
                    } catch (e: Exception) {
                        Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                        null
                    }
                }
                withContext(Dispatchers.Main) {
                    _movieResponse.value = result
                }
            }
        }
    }
    fun fetchSimilar() {
        viewModelScope.launch{
            CoroutineScope(Dispatchers.IO).launch{
                delayRandomMillis()
                val result = withContext(Dispatchers.IO) {
                    try {
                        MovieApi.retrofitService.getSimilar(movieId.value)
                    } catch (e: Exception) {
                        Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                        null
                    }
                }
                withContext(Dispatchers.Main) {
                    _similarResponse.value = result
                }
            }
        }
    }
    fun fetchReviews() {
        viewModelScope.launch{
            CoroutineScope(Dispatchers.IO).launch {
                delayRandomMillis()
                val result = withContext(Dispatchers.IO) {
                    try {
                        MovieApi.retrofitService.getReviewsById(movieId.value)
                    } catch (e: Exception) {
                        Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                        null
                    }
                }
                withContext(Dispatchers.Main) {
                    _review.value = result
                }
            }
        }
    }
    fun fetchDetail() {
        viewModelScope.launch{
            CoroutineScope(Dispatchers.IO).launch {
                delayRandomMillis()
                val result = withContext(Dispatchers.IO) {
                    try {
                        MovieApi.retrofitService.getDetailById(movieId.value)
                    } catch (e: Exception) {
                        Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                        null
                    }
                }
                withContext(Dispatchers.Main) {
                    _movieDetail.value = result
                }
            }
        }
    }
    fun fetchReview () {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                delayRandomMillis()
                val result = withContext(Dispatchers.IO) {
                    try {
                        MovieApi.retrofitService.getReviewsById(movieId.value, page = reviewPageNo.value)
                    } catch (e: Exception) {
                        Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                        null
                    }
                }
                withContext(Dispatchers.Main) {
                    _review.value = result
                }
            }

        }
    }
    fun fetchSearch() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                delayRandomMillis()
            }
        }
    }
    fun updateUrl(newUrl: String) {
        _searchCriteria.value = _searchCriteria.value.copy(query = newUrl)
    }
    fun updatePageNumber(newPageNumber: String) {
        val totalPages = _movieResponse.value?.totalPages?.minus(5000) ?: 1
        val newPN = newPageNumber.filter { it.isDigit()}
        if (newPN.isEmpty() || newPN.toInt() < 1 || newPN.toInt() > totalPages) {
            _searchCriteria.value = _searchCriteria.value.copy(pageNumber = 1)
        }
        else if (newPN.toInt() > totalPages) {
            _searchCriteria.value = _searchCriteria.value.copy(
                pageNumber = _movieResponse.value?.totalPages ?: 1)
        }
        else {
            _searchCriteria.value = _searchCriteria.value.copy(pageNumber = newPN.toInt())
        }
    }
    fun updateReviewPageNumber(newPageNumber: String) {
        val totalPages = _review.value?.totalPages?.minus(5000) ?: 1
        val newPN = newPageNumber.filter { it.isDigit()}
        if (newPN.isEmpty() || newPN.toInt() < 1 || newPN.toInt() > totalPages ) {
            _reviewPageNo.value = 1
        }
        else if (newPN.toInt() > totalPages) {
            _reviewPageNo.value = _review.value?.totalPages ?: 1
        }
        else {
            _reviewPageNo.value = newPN.toInt()
        }
    }
    fun updateMovieId(newId: Int) {
        _movieId.value = newId
    }

}

