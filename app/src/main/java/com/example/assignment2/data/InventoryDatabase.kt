package com.example.assignment2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, MovieTable::class, FavouriteMovieTable::class], version = 7, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    fun userRepoWrapper() : UsersRepository {
        return UsersRepository(userDao())
    }
    abstract fun movieDao(): MovieDao
    fun movieRepoWrapper() : MovieRepository {
        return MovieRepository(movieDao())
    }
    abstract fun favouriteMovieDao(): FavouriteMovieDao
    fun favouriteMovieRepoWrapper() : FavouriteMovieRepository {
        return FavouriteMovieRepository(favouriteMovieDao())
    }

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "tables")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}