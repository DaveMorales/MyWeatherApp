package com.david.myweather

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.david.myweather.di.component.DaggerAppComponent
import com.david.myweather.di.modules.NetworkModule
import com.david.myweather.di.modules.AppModule
import com.david.myweather.utils.Constants
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MyWeatherApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(Constants.BASE_URL))
            .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}