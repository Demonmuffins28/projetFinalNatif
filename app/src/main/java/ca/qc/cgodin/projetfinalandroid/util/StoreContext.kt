package ca.qc.cgodin.projetfinalandroid.util

import android.app.Application
import android.content.Context

class storeContext  : Application() {
    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        var instance: storeContext? = null
            private set

        val context: Context?
            get() = instance
    }
}