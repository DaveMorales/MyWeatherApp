package com.david.myweather.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.net.NetworkInfo
import android.net.ConnectivityManager

class ConnectivityChangeReceiver: BroadcastReceiver() {

    val TAG:String = "ConnectivityChange"
    private var listener: ConnectivityListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {

        listener=(context as ConnectivityListener)
        listener!!.onConectivityChanged(isOnline(context))
    }

    private fun isOnline(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            //should check null because in airplane mode it will be null
            return netInfo != null && netInfo.isConnected
        } catch (e: NullPointerException) {
            e.printStackTrace()
            return false
        }

    }
}