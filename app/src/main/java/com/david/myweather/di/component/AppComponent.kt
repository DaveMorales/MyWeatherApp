package com.david.myweather.di.component

import com.david.myweather.di.modules.NetworkModule
import com.david.myweather.MyWeatherApplication
import com.david.myweather.di.modules.AppModule
import com.david.myweather.di.modules.BuildersModule
import com.david.myweather.utils.UpdateWorker
import com.david.myweather.weatherList.WeatherListFragment
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class, BuildersModule::class, AppModule::class,
        NetworkModule::class
    )
)
interface AppComponent {
    fun inject(app: MyWeatherApplication)

    fun inject(worker: UpdateWorker)
}