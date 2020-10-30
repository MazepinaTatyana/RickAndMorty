package com.example.rickandmorty.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.pojo.Result

@Dao
interface RickAndMortyDao {

    @Query("SELECT * FROM RandM")
    fun getInfoAboutRandM() : DataSource.Factory<Int, Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInfoAboutRandM(info : List<Result>)

    @Query("DELETE FROM RandM")
    fun deleteAllInfo()

    @Query("SELECT * FROM RandM WHERE id ==:id")
    fun getCharacterById(id : Int) : LiveData<Result>

}