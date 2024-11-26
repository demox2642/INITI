package com.example.initi_test_project.di

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import javax.inject.Inject

class ConnectionManager
    @Inject
    constructor(
        private val context: Context,
    ) {
        val connectionLiveData = ConnectionLiveData(context)

        @RequiresApi(Build.VERSION_CODES.M)
        fun isConnected(): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val network = cm?.activeNetwork
            return if (network != null) {
                val nc = cm.getNetworkCapabilities(network)
                nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    nc.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI,
                    )
            } else {
                false
            }
        }

        inner class ConnectionLiveData(private val context: Context) : LiveData<Boolean>() {
            private val networkReceiver =
                object : BroadcastReceiver() {
                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun onReceive(
                        context: Context,
                        intent: Intent,
                    ) {
                        postValue(isConnected())
                    }
                }

            override fun onActive() {
                super.onActive()
                context.registerReceiver(
                    networkReceiver,
                    IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION),
                )
            }

            override fun onInactive() {
                super.onInactive()
                try {
                    context.unregisterReceiver(networkReceiver)
                } catch (e: Exception) {
                }
            }
        }
    }
