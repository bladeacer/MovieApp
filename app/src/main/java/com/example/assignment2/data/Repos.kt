package com.example.assignment2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.google.gson.annotations.SerializedName

import kotlinx.coroutines.flow.Flow
/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val password: String,
    val email: String
)

@Entity(tableName = "movies")
data class MovieTable(
    val adult: Boolean,
    val backdropPath: String?,
    @PrimaryKey
    val id: Int,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

@Entity(tableName = "favourite_movies")
data class FavouriteMovieTable(
    val userId: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val id: Int,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    @PrimaryKey(autoGenerate = true)
    val favId: Int = 0,
)

/**
 * Database access object to access the Inventory database
 */
@Dao
interface UserDao {

    @Query("SELECT * from users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * from users WHERE id = :id")
    fun getUser(id: Int): Flow<User?>

    @Query("SELECT * from users WHERE email = :email AND password = :password")
    fun validateLogin(email: String, password: String): Flow<User?>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing User into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: MovieTable)

    @Query("SELECT * from movies ORDER BY title ASC")
    fun getAllMovies(): Flow<List<MovieTable>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): Flow<MovieTable?>

}

@Dao
interface FavouriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: FavouriteMovieTable)

    @Query("SELECT * from favourite_movies WHERE userId = :userId ORDER BY title ASC")
    fun getAllMovies(userId: Int): Flow<List<FavouriteMovieTable>>

    @Query("SELECT * FROM favourite_movies WHERE id = :movieId AND userId = :userId")
    fun getMovie(movieId: Int, userId: Int): Flow<FavouriteMovieTable?>

    @Query("DELETE FROM favourite_movies WHERE id = :movieId AND userId = :userId")
    suspend fun deleteMovieById(movieId: Int, userId: Int)

}


interface UsersInterface {
    /**
     * Retrieve all the users from the the given data source.
     */
    fun getAllUsersStream(): Flow<List<User>>

    /**
     * Retrieve an user from the given data source that matches with the [id].
     */
    fun getUserStream(id: Int): Flow<User?>
    fun validateLogin(email: String, password: String): Flow<User?>

    /**
     * Insert user in the data source
     */
    suspend fun insertUser(user: User)

    /**
     * Delete user from the data source
     */
    suspend fun deleteUser(user: User)

    /**
     * Update user in the data source
     */
    suspend fun updateUser(user: User)
}

interface MovieInterface {
    fun getAllMoviesStream(): Flow<List<MovieTable>>

    fun getMovieStream(movieId: Int): Flow<MovieTable?>

    suspend fun insertMovie(movie: MovieTable)
}

interface FavouriteMovieInterface {
    fun getAllMoviesStream(userId: Int): Flow<List<FavouriteMovieTable>>
    fun getMovieStream(movieId: Int, userId: Int): Flow<FavouriteMovieTable?>
    suspend fun deleteFavouriteMovieById(movieId: Int, userId: Int)
    suspend fun insertMovie(movie: FavouriteMovieTable)
}

class UsersRepository (
    private val userDao: UserDao,
) : UsersInterface {

    override fun getAllUsersStream(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    override fun getUserStream(id: Int): Flow<User?> {
        return userDao.getUser(id)
    }

    override fun validateLogin(email: String, password: String): Flow<User?> {
        return userDao.validateLogin(email, password)
    }

    override suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    override suspend fun updateUser(user: User) {
        return userDao.update(user)
    }
    override suspend fun deleteUser(user: User) {
        return userDao.delete(user)
    }
}

class MovieRepository(
    private val movieDao: MovieDao
) : MovieInterface{
    override fun getAllMoviesStream(): Flow<List<MovieTable>> {
        return movieDao.getAllMovies()
    }
    override fun getMovieStream(movieId: Int): Flow<MovieTable?> {
        return movieDao.getMovie(movieId)
    }

    override suspend fun insertMovie(movie: MovieTable) {
        return movieDao.insert(movie)
    }

}

class FavouriteMovieRepository(
    private val favouriteMovieDao: FavouriteMovieDao
) : FavouriteMovieInterface {
    override fun getAllMoviesStream(userId: Int): Flow<List<FavouriteMovieTable>> {
        return favouriteMovieDao.getAllMovies(userId)
    }

    override fun getMovieStream(movieId: Int, userId: Int): Flow<FavouriteMovieTable?> {
        return favouriteMovieDao.getMovie(movieId, userId)
    }

    override suspend fun deleteFavouriteMovieById(movieId: Int, userId: Int) {
        return favouriteMovieDao.deleteMovieById(movieId, userId)
    }

    override suspend fun insertMovie(movie: FavouriteMovieTable) {
        return favouriteMovieDao.insert(movie)
    }
}