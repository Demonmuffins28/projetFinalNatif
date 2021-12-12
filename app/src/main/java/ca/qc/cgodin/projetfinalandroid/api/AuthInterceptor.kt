package ca.qc.cgodin.projetfinalandroid.api

import android.content.Context
import ca.qc.cgodin.projetfinalandroid.util.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) {
    private val sessionManager = SessionManager(context)

    fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // if token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}