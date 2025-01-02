package com.example.assignment2

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.utils.IdSearchCriteria
import com.example.assignment2.utils.InventoryApplication
import com.example.assignment2.utils.Movie
import com.example.assignment2.utils.MovieDetail
import com.example.assignment2.utils.MovieResponse
import com.example.assignment2.utils.RequestUrl
import com.example.assignment2.utils.Review
import com.example.assignment2.utils.SearchCriteria
import com.example.assignment2.utils.User
import com.example.assignment2.utils.UsersRepository
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

class MyViewModel( private val repository: UsersRepository) : ViewModel() {
    private val _toggleCriteria = MutableStateFlow(SearchCriteria(RequestUrl.POPULAR.url, 1))
    val searchCriteria: StateFlow<SearchCriteria> = _toggleCriteria.asStateFlow()

    private val _movieResponse = MutableStateFlow<MovieResponse?>(null)
    val movieResponse: StateFlow<MovieResponse?> = _movieResponse.asStateFlow()

    private val _similarResponse = MutableStateFlow<MovieResponse?>(null)
    val similarResponse: StateFlow<MovieResponse?> = _similarResponse.asStateFlow()

    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?> = _movieDetail.asStateFlow()

    private val _review = MutableStateFlow<Review?>(null)
    val review: StateFlow<Review?> = _review.asStateFlow()

    private val _search = MutableStateFlow<SearchCriteria?>(null)
    val search: StateFlow<SearchCriteria?> = _search.asStateFlow()

    private val _searchResult = MutableStateFlow<MovieResponse?>(null)
    val keyword: StateFlow<MovieResponse?> = _searchResult.asStateFlow()

    private val _movieId = MutableStateFlow<Int>(1)
    val movieId: StateFlow<Int> = _movieId.asStateFlow()

    private val _reviewCriteria = MutableStateFlow<IdSearchCriteria>(IdSearchCriteria(1, 1))
    val reviewCriteria: StateFlow<IdSearchCriteria> = _reviewCriteria.asStateFlow()

    private val _user = MutableStateFlow<User>(User(0, "", "", ""))
    val user: StateFlow<User> = _user.asStateFlow()

    suspend fun addUser() {
        viewModelScope.launch {
            repository.insertUser(user.value)
        }
    }

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
                        MovieApi.retrofitService.getReviewsById(reviewCriteria.value.id,
                            page = reviewCriteria.value.pageNumber)
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
        // TODO: There is no way this works
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                delayRandomMillis()
            }
            val result = withContext(Dispatchers.IO) {
                try {
                    MovieApi.retrofitService.getKeyword(search.value?.query ?: "")
                } catch (e: Exception) {
                    Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                    null
                }
            }
            withContext(Dispatchers.Main) {
                var processedMovieList: List<Movie> = listOf<Movie>()
                result?.results?.forEach { searchEntry ->
                    val result2 = withContext(Dispatchers.IO) {
                        delayRandomMillis(0, 500)
                        try {
                            MovieApi.retrofitService.getDetailById(searchEntry.id)
                        } catch (e: Exception) {
                            Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                            null
                        }
                    }
                    val result3 = Movie(result2?.adult == false,
                        result2?.backdropPath, result2?.genres?.map { genre -> genre.id } ?: listOf<Int>(),
                        result2?.id ?: 0,
                        result2?.originalLanguage,
                        result2?.originalTitle,
                        result2?.overview,
                        result2?.popularity ?: 0.0,
                        result2?.posterPath,
                        result2?.releaseDate,
                        result2?.title, result2?.video == true, result2?.voteAverage ?: 0.0,
                        result2?.voteCount ?: 0)
                    processedMovieList = processedMovieList + result3
                }
                val processedResult = MovieResponse(
                    result?.page ?: 1,
                    results = processedMovieList,
                    result?.totalPages ?: 0,
                    result?.totalResults ?: 0
                )
                _searchResult.value = processedResult
            }
        }
    }
    fun updateUrl(newUrl: String) {
        _toggleCriteria.value = _toggleCriteria.value.copy(query = newUrl)
    }
    fun updatePageNumber(newPageNumber: String) {
        val totalPages = _movieResponse.value?.totalPages?.minus(5000) ?: 1
        val newPN = newPageNumber.filter { it.isDigit()}
        if (newPN.isEmpty() || newPN.toInt() < 1 || newPN.toInt() > totalPages) {
            _toggleCriteria.value = _toggleCriteria.value.copy(pageNumber = 1)
        }
        else if (newPN.toInt() > totalPages) {
            _toggleCriteria.value = _toggleCriteria.value.copy(
                pageNumber = _movieResponse.value?.totalPages ?: 1)
        }
        else {
            _toggleCriteria.value = _toggleCriteria.value.copy(pageNumber = newPN.toInt())
        }
    }
    fun updateMovieId(newId: Int) {
        _movieId.value = newId
    }
    fun updateUser(newUser: User) {
        _user.value = newUser
    }
    fun updateSearch(newSearch: SearchCriteria) {
        _search.value = newSearch
    }
    fun updateReviewCriteria(newReviewCriteria: IdSearchCriteria) {
        _reviewCriteria.value = newReviewCriteria
    }

}

