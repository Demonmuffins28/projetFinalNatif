package ca.qc.cgodin.projetfinalandroid.api

import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException


class TokenInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())

        // if 'x-auth-token' is available into the response header
        // save the new token into session.The header key can be
        // different upon implementation of backend.
        val newToken: String = response.header("x-auth-token")
        if (newToken != null) {
            session.saveToken(newToken)
        }
        return response
    }

    init {
        this.session = session
    }
}