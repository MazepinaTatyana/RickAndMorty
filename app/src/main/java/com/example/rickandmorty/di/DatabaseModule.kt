//package com.example.rickandmorty.di
//
//import android.content.Context
//import androidx.appcompat.app.AppCompatActivity
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.rickandmorty.database.RickAndMortyDao
//import com.example.rickandmorty.database.RickAndMortyDataBase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ApplicationComponent
//import dagger.hilt.android.qualifiers.ApplicationContext
//import javax.inject.Singleton
//
//@InstallIn(ApplicationComponent::class)
//@Module
//object DatabaseModule {
//
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext appContext: Context): RickAndMortyDataBase {
//        return Room.databaseBuilder(
//            appContext,
//            RickAndMortyDataBase::class.java,
//            "main.db"
//        )
//            .build()
//    }
//
//    @Provides
//    fun providerDao(dataBase: RickAndMortyDataBase) : RickAndMortyDao {
//        return dataBase.rickAndMortyDao()
//    }
//}