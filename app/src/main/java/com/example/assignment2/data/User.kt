package com.example.assignment2.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

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

/**
 * Database access object to access the Inventory database
 */
@Dao
interface UserDao {

    @Query("SELECT * from users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * from users WHERE id = :id")
    fun getUser(id: Int): Flow<User>

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

interface UsersRepository {
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

class MyRepositoryImpl (
    private val myDao: UserDao
) : UsersRepository {

    override fun getAllUsersStream(): Flow<List<User>> {
        return myDao.getAllUsers()
    }

    override fun getUserStream(id: Int): Flow<User?> {
        return myDao.getUser(id)
    }

    override fun validateLogin(email: String, password: String): Flow<User?> {
        return myDao.validateLogin(email, password)
    }

    override suspend fun insertUser(user: User) {
        myDao.insert(user)
    }

    override suspend fun updateUser(user: User) {
        return myDao.update(user)
    }
    override suspend fun deleteUser(user: User) {
        return myDao.delete(user)
    }
}
