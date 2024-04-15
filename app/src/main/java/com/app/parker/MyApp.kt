package com.app.parker

import android.app.Application
import com.app.parker.constant.CommonUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CommonUtil.appContext = this
    }
}