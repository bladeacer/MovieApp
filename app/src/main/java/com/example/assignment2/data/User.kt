package com.example.assignment2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.assignment2.utils.Collection
import com.example.assignment2.utils.Genre

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
data class Movie(
    val backdropPath: String?,
    val belongsToCollection: Collection?,
    val budget: Int,
    val genres: List<Genre>?,
    val homepage: String,
//    Use movieId form Api as primary key, remove prodCompanies, countries, spoken langs when calling
//    write to the movie DAO
    @PrimaryKey
    val id: Int = 0,
    val imdbID: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double = 0.0,
    val posterPath: String?,
    val releaseDate: String?,
    val revenue: Int = 0,
    val runtime: Int = 0,
    val status: String,
    val tagline: String,
    val title: String?,
    val video: Boolean = true,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
)

@Entity(
    tableName = "user_movie_cross_ref",
    primaryKeys = ["user_id", "movie_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserMovieCrossRef(
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Int
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
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): Flow<Movie?>
}

data class UserWithFavoriteMovies(
    @androidx.room.Embedded
    val user: User,
    @androidx.room.Relation(
        parentColumn = "id",
        entityColumn = "movie_id",
        associateBy = androidx.room.Junction(UserMovieCrossRef::class)
    )
    val movies: List<Movie>
)

@Dao
interface UserMovieCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(crossRef: UserMovieCrossRef)

    @Delete
    suspend fun delete(crossRef: UserMovieCrossRef)

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserWithFavoriteMovies(userId: Int): Flow<UserWithFavoriteMovies>
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
