package com.david.myweather.utils

import android.content.Context

import androidx.work.WorkerParameters
import io.reactivex.Single
import androidx.work.RxWorker
import androidx.work.Worker
import com.david.myweather.data.source.WeatherRepository
import javax.inject.Inject


class UpdateWorker(
    context: Context,
    params: WorkerParameters, @Inject private val weatherRepository: WeatherRepository
) : Worker(context, params) {
    override fun doWork(): Result {
        return Result.success();
    }

}