package com.david.myweather.di.modules

import android.app.Application
import android.arch.lifecycle.ViewModelProvider

import android.arch.persistence.room.Room
import com.david.myweather.data.source.WeatherRepository
import com.david.myweather.data.source.local.Database
import com.david.myweather.data.source.local.WeatherDao
import com.david.myweather.utils.Constants
import com.david.myweather.utils.Utils
import com.david.myweather.weatherList.WeatherListViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application): Database = Room.databaseBuilder(
        app,
        Database::class.java, Constants.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providWeatherDao(
        database: Database
    ): WeatherDao = database.weatherDao()

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherRepository: WeatherRepository
    ): WeatherRepository = weatherRepository

    @Provides
    @Singleton
    fun providWeatherListViewModelFactory(
        factory: WeatherListViewModelFactory
    ): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun provideUtils(): Utils = Utils(app)

}