package ca.qc.cgodin.projetfinalandroid.util

import android.content.SharedPreferences

import android.app.Activity
import android.content.Context
import ca.qc.cgodin.projetfinalandroid.R


object SharedPref {
    private var mSharedPref: SharedPreferences? = null
    const val USER_TOKEN = "token_utilisateur"
    const val USER_ID = "id_util"

    fun init(context: Context) {
        if (mSharedPref == null) mSharedPref =
            context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE)
    }

    fun clearStoredData(context: Context) {
        val editor = mSharedPref!!.edit()
        editor.remove(context.getString(R.string.app_name))
        editor.clear()
        editor.apply()
    }

    fun read(key: String?, defValue: String?): String? {
        return mSharedPref!!.getString(key, defValue)
    }

    fun write(key: String?, value: String?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.commit()
    }

    fun read(key: String?, defValue: Boolean): Boolean {
        return mSharedPref!!.getBoolean(key, defValue)
    }

    fun write(key: String?, value: Boolean) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.commit()
    }

    fun read(key: String?, defValue: Int): Int {
        return mSharedPref!!.getInt(key, defValue)
    }

    fun write(key: String?, value: Int?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt(key, value!!).commit()
    }
}