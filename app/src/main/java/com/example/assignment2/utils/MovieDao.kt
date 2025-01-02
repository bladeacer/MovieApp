package com.example.assignment2.utils

import android.app.Application
import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val password: String,
    val preferredName: String
)

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)
    @Update
    suspend fun updateUser(user: User)
    @Delete
    suspend fun deleteUser(user: User)
    @Query("SELECT * from users WHERE id = :id")
    fun getUser(id: Int): Flow<User>
    @Query("SELECT * from users ORDER BY preferredName ASC")
    fun getAllUsers(): Flow<List<User>>
}

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "user_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

class UsersRepository(private val userDao: UserDao) {
    /**
     * Retrieve all the users from the the given data source.
     */
    fun getAllUsersStream(): Flow<List<User>> = userDao.getAllUsers()

    /**
     * Retrieve an user from the given data source that matches with the [id].
     */
    fun getUserStream(id: Int): Flow<User?> = userDao.getUser(id)

    /**
     * Insert user in the data source
     */
    suspend fun insertUser(user: User) = userDao.insertUser(user)

    /**
     * Delete user from the data source
     */
    suspend fun deleteUser(user: User)= userDao.deleteUser(user)

    /**
     * Update user in the data source
     */
    suspend fun updateUser(user: User)= userDao.updateUser(user)
}

interface AppContainer {
    val usersRepository: UsersRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val usersRepository: UsersRepository by lazy {
        UsersRepository(InventoryDatabase.getDatabase(context).userDao())
    }
}

class InventoryApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}
