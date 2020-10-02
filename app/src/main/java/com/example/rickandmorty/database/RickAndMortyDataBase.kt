package com.example.rickandmorty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmorty.pojo.Result


@Database(entities = [Result::class], version = 1, exportSchema = false)
abstract class RickAndMortyDataBase : RoomDatabase()  {
    companion object {
        private var db : RickAndMortyDataBase? = null
        const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context) : RickAndMortyDataBase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        RickAndMortyDataBase::class.java,
                        DB_NAME
                    )   .fallbackToDestructiveMigration()
                        .build()
                db = instance
                return instance
            }
        }
    }
    abstract fun rickAndMortyDao() : RickAndMortyDao
}