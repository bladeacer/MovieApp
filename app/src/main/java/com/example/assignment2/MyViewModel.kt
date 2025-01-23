package com.example.assignment2

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.assignment2.data.FavouriteMovieRepository
import com.example.assignment2.data.FavouriteMovieTable
import com.example.assignment2.data.MovieRepository
import com.example.assignment2.data.MovieTable
import com.example.assignment2.data.UsersRepository
import com.example.assignment2.data.User
import com.example.assignment2.utils.Movie
import com.example.assignment2.utils.MovieDetail
import com.example.assignment2.utils.MovieResponse
import com.example.assignment2.utils.NetworkCheck
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext


class MyViewModel (
    private val usersRepo: UsersRepository,
    private val movieRepo: MovieRepository,
    private val favouriteMovieRepo: FavouriteMovieRepository
) : ViewModel() {

    private val _toggleCriteria = MutableStateFlow(SearchCriteria(RequestUrl.POPULAR.url, 1))
    val toggleCriteria: StateFlow<SearchCriteria> = _toggleCriteria.asStateFlow()

    private val _movieResponse = MutableStateFlow<MovieResponse?>(null)
    val movieResponse: StateFlow<MovieResponse?> = _movieResponse.asStateFlow()

    private val _similarResponse = MutableStateFlow<MovieResponse?>(null)
    val similarResponse: StateFlow<MovieResponse?> = _similarResponse.asStateFlow()

    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?> = _movieDetail.asStateFlow()

    private val _review = MutableStateFlow<Review?>(null)
    val review: StateFlow<Review?> = _review.asStateFlow()

    private val _search = MutableStateFlow<SearchCriteria>(SearchCriteria("", 1))
    val search: StateFlow<SearchCriteria> = _search.asStateFlow()

    private val _searchResult = MutableStateFlow<MovieResponse?>(null)
    val searchResult: StateFlow<MovieResponse?> = _searchResult.asStateFlow()

    private val _movieId = MutableStateFlow<Int>(0)
    val movieId: StateFlow<Int> = _movieId.asStateFlow()

    private val _allowAction = MutableStateFlow<Boolean>(false)
    val allowAction: StateFlow<Boolean> = _allowAction.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isFavMovie = MutableStateFlow<Boolean>(false)
    val isFavMovie: StateFlow<Boolean> = _isFavMovie.asStateFlow()

    private val _favouriteMovies = MutableStateFlow<List<FavouriteMovieTable>>(listOf())
    val favouriteMovies: StateFlow<List<FavouriteMovieTable>> = _favouriteMovies.asStateFlow()

    private val _favDetail = MutableStateFlow<FavouriteMovieTable?>(null)
    val favDetail: StateFlow<FavouriteMovieTable?> = _favDetail.asStateFlow()

    private val _isOnline = MutableStateFlow<Boolean>(true)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    private val _offlineMovies = MutableStateFlow<List<MovieTable>>(listOf())
    val offlineMovies: StateFlow<List<MovieTable>> = _offlineMovies.asStateFlow()

    private val _offlineMovie = MutableStateFlow<MovieTable?>(null)
    val offlineMovie: StateFlow<MovieTable?> = _offlineMovie.asStateFlow()

    fun isOnline(context: Context) {
        _isOnline.value = NetworkCheck(context).isInternetAvailable()
    }
    fun addUser(user: User) {
        viewModelScope.launch {
            try {
                usersRepo.insertUser(user)
            } catch (e: Exception) {
                Log.d("User Response Exception", "Message: ${e.message.toString()}")
            }
        }
    }
    fun getUser(id: Int) : User? {
        viewModelScope.launch{
            CoroutineScope(Dispatchers.IO).launch {
                val result = withContext(Dispatchers.IO) {
                    try{
                        usersRepo.getUserStream(id)
                    } catch (e: Exception) {
                        Log.d("User Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                        null
                    }
                }
                withContext(Dispatchers.Main) {
                    _user.value = result?.first()
                }
            }
        }
        return _user.value
    }
//    private val _uiState = MutableStateFlow<UiState<Any>>(UiState.Loading)
//    val uiState: StateFlow<UiState<Any>> = _uiState.asStateFlow()

    fun fetchMovies() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val result = withContext(Dispatchers.IO) {
                    try {
                        MovieApi.retrofitService.getMovies(toggleCriteria.value.query, page = toggleCriteria.value.pageNumber)
                    } catch (e: Exception) {
                        Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                        null
                    }
                }
                withContext(Dispatchers.Main) {
                    _movieResponse.value = result
                    result?.results?.forEach { movie ->
                        insertMovie(movie)
                    }
                }
            }
        }
    }
    fun fetchSimilar() {
        viewModelScope.launch{
            CoroutineScope(Dispatchers.IO).launch{
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
                    result?.results?.forEach { movie ->
                        insertMovie(movie)
                    }
                }
            }
        }
    }
    fun fetchReviews() {
        viewModelScope.launch{
            CoroutineScope(Dispatchers.IO).launch {
                val result = withContext(Dispatchers.IO) {
                    try {
                        MovieApi.retrofitService.getReviewsById(_movieId.value)
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
                val result = withContext(Dispatchers.IO) {
                    try {
                        Log.d("Movie Response", "Movie Id value: ${_movieId.value}")
                        MovieApi.retrofitService.getDetailById(_movieId.value)
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

    //    TODO: Index keywords for each user,
    fun fetchSearch() {
        // TODO: Refactor search knowing that it is keywords
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val result = withContext(Dispatchers.IO) {
                    try {
                        MovieApi.retrofitService.getSearch(search.value.query, search.value.pageNumber)
                    } catch (e: Exception) {
                        Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
                        null
                    }
                }
                withContext(Dispatchers.Main) {
                    _searchResult.value = result
//                var processedMovieList: List<GenreOrKeyword> = listOf<GenreOrKeyword>()
//                result?.results?.forEach { searchEntry ->
//                    val result2 = withContext(Dispatchers.IO) {
//                        try {
//                            MovieApi.retrofitService.getDetailById(searchEntry.id)
//                        } catch (e: Exception) {
//                            Log.d("Movie Response Exception: ", "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}")
//                            null
//                        }
//                    }
//                    val result3 = Movie(result2?.adult == false,
//                        result2?.backdropPath, result2?.genres?.map { genre -> genre.id } ?: listOf<Int>(),
//                        result2?.id ?: 0,
//                        result2?.originalLanguage,
//                        result2?.originalTitle,
//                        result2?.overview,
//                        result2?.popularity ?: 0.0,
//                        result2?.posterPath,
//                        result2?.releaseDate,
//                        result2?.title, result2?.video == true, result2?.voteAverage ?: 0.0,
//                        result2?.voteCount ?: 0)
//                    processedMovieList = processedMovieList + result3
//                }
//                val processedResult = MovieResponse(
//                    result?.page ?: 1,
//                    results = processedMovieList,
//                    result?.totalPages ?: 0,
//                    result?.totalResults ?: 0
//                )
//                _searchResult.value = processedResult
                }
            }

        }
    }
    fun updateUrl(newUrl: String) {
        _toggleCriteria.value = _toggleCriteria.value.copy(query = newUrl)
    }
    fun updatePageNumber(newPageNumber: String) {
        val totalPages = _movieResponse.value?.totalPages?.let{
            (it * 0.9).toInt()
        } ?: 1
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
    fun updateSearch(newSearch: SearchCriteria) {
        _search.value = newSearch
    }
    fun validateLogin(email: String, password: String) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
            }
            val result = withContext(Dispatchers.IO) {
                try {
                    usersRepo.validateLogin(email, password)

                } catch (e: Exception) {
                    Log.d(
                        "Movie Response Exception: ",
                        "Message: ${e.message.toString()}\nStack trace: ${e.printStackTrace()}"
                    )
                    null
                }
            }

            _allowAction.value = result != null
            result?.collect {
                    value -> _user.value = value
            }
        }
    }
    fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                val newMovie = MovieTable(movie.adult, movie.backdropPath, movie.id,
                    movie.originalLanguage, movie.originalTitle, movie.overview,
                    movie.popularity, movie.posterPath, movie.releaseDate, movie.title,
                    movie.video, movie.voteAverage, movie.voteCount)
                movieRepo.insertMovie(newMovie)
            } catch (e: Exception) {
                Log.d("Response Exception", "Message: ${e.message.toString()}")
            }
        }
    }
    fun getOfflineMovies() {
        viewModelScope.launch {
            try {
                 val result = movieRepo.getAllMoviesStream()
                result.collect {
                    value -> _offlineMovies.value = value
                }
            } catch (e: Exception) {
                Log.d("Response Exception", "Message: ${e.message.toString()}")
            }
        }
    }
    fun insertFavouriteMovie(movie: MovieDetail?) {
        viewModelScope.launch {
            try {
                if (movie == null) throw Exception("Movie is null")
                val newMovie = FavouriteMovieTable(
                    _user.value?.id ?: 0, movie.adult, movie.backdropPath, movie.id,
                    movie.originalLanguage, movie.originalTitle, movie.overview,
                    movie.popularity, movie.posterPath, movie.releaseDate, movie.title,
                    movie.video,  movie.voteAverage, movie.voteCount)
                favouriteMovieRepo.insertMovie(newMovie)
            } catch (e: Exception) {
                Log.d("Response Exception", "Message: ${e.message.toString()}")
            }
        }
    }
    fun deleteFavouriteMovieById(movieId: Int) {
        viewModelScope.launch {
            try {
                favouriteMovieRepo.deleteFavouriteMovieById(movieId, _user.value?.id ?: 0)
            } catch (e: Exception) {
                Log.d("Response Exception", "Message: ${e.message.toString()}")
            }
        }
    }
    fun getFavouriteMovieById(movieId: Int) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {}
            val result = withContext(Dispatchers.IO) {
                try {
                    favouriteMovieRepo.getMovieStream(movieId, _user.value?.id ?: 0)
                } catch (e: Exception) {
                    Log.d("Response Exception", "Message: ${e.message.toString()}")
                    null
                }
            }
            withContext(Dispatchers.Main) {
                _isFavMovie.value = result == null
                result?.collect { value ->
                    _favDetail.value = value
                }
            }
        }

    }
    fun getFavouriteMovies() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {}
            val result = withContext(Dispatchers.IO) {
                try {
                    favouriteMovieRepo.getAllMoviesStream(user.value?.id ?: 0)
                } catch (e: Exception) {
                    Log.d("Response Exception", "Message: ${e.message.toString()}")
                    null
                }
            }
            withContext(Dispatchers.Main) {
                result?.collect {value ->
                    _favouriteMovies.value = value
                }
            }
        }
    }
    fun getOfflineMovieById() {
        viewModelScope.launch{
            CoroutineScope(Dispatchers.IO).launch {}
            val result = withContext(Dispatchers.IO) {
                try {
                    movieRepo.getMovieStream(_movieId.value)
                } catch (e: Exception) {
                    Log.d("Response Exception", "Message: ${e.message.toString()}")
                    null
                }
            }
            withContext(Dispatchers.Main) {
                result?.collect {value ->
                    _offlineMovie.value = value
                }
            }
        }
    }

    fun updateIsFavMovie(newIsFav: Boolean) {
        _isFavMovie.value = newIsFav
    }
    fun updateCurrentUser(newUser: User) {
        _user.value = newUser
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieApplication)
                MyViewModel(application.database.userRepoWrapper(),
                    application.database.movieRepoWrapper(),
                    application.database.favouriteMovieRepoWrapper())
            }
        }
    }


}

