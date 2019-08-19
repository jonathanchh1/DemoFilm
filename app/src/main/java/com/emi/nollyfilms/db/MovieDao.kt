package com.emi.nollyfilms.db


import androidx.paging.DataSource
import androidx.room.*
import com.emi.nollyfilms.model.Movies



@Dao
interface MovieDao {


    @Query("Select * from table_movies order by title desc")
    fun getAllMovies(): DataSource.Factory<Int, Movies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies : Movies) : Long

    @Query("Delete from table_movies")
    fun deleteAll()
}