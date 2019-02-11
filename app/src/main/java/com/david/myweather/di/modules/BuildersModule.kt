package com.david.myweather.di.modules

import com.david.myweather.addCity.AddCityFragment
import com.david.myweather.weatherList.WeatherListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

  @ContributesAndroidInjector
  internal abstract fun contributeWeatherListFragment(): WeatherListFragment

  @ContributesAndroidInjector
  internal abstract fun contributeAddCityFragment(): AddCityFragment


}