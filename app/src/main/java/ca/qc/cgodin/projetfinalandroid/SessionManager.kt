package ca.qc.cgodin.projetfinalandroid

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val SESSION_EXPIRY_TIME = ""

    companion object {
        const val USER_TOKEN = "token_utilisateur"
        const val USER_ID = "id_util"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun saveUserId(userId: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, userId)
        editor.apply()
    }

    fun fetchUserId(): Int? {
        return prefs.getInt(USER_ID, 0)
    }

    fun startUserSession(context: Context, expiresIn: Int) {
        val calendar = Calendar.getInstance()
        val userLoggedInTime = calendar.time
        calendar.time = userLoggedInTime
        calendar.add(Calendar.SECOND, expiresIn)
        val expiryTime = calendar.time
        val editor = prefs.edit()
        editor.putLong(SESSION_EXPIRY_TIME, expiryTime.time)
        editor.apply()
    }

    fun isSessionActive(currentTime: Date, context: Context) : Boolean {
        val sessionExpiresAt = Date(getExpiryDateFromPreferences(context)!!)
        return !currentTime.after(sessionExpiresAt)
    }

    private fun getExpiryDateFromPreferences(context: Context): Long? {
        return prefs.getLong(SESSION_EXPIRY_TIME, 0)
    }

    fun endUserSession(context: Context) {
        clearStoredData(context)
    }

    private fun clearStoredData(context: Context) {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}