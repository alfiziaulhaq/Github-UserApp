package com.dicoding.alfigithubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: Favorite)

    @Update
    fun update(user : Favorite)

    @Query("SELECT * from Favorite ORDER BY username ASC")
    fun getAll(): LiveData<List<Favorite>>

    @Delete
    fun delete(user: Favorite)

    @Query("SELECT * FROM Favorite WHERE username = :username")
    fun getUser(username: String): LiveData<Favorite>
}