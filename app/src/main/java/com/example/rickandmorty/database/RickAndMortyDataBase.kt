package com.example.rickandmorty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.converters.Converter
import com.example.rickandmorty.pojo.Result


@Database(entities = [Result::class], version = 6, exportSchema = false)
@TypeConverters(value = [Converter::class])
abstract class RickAndMortyDataBase : RoomDatabase() {
    companion object {
        private var db: RickAndMortyDataBase? = null
        const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): RickAndMortyDataBase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        RickAndMortyDataBase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun rickAndMortyDao(): RickAndMortyDao
}