package ca.qc.cgodin.projetfinalandroid.api

import android.webkit.HttpAuthHandler
import ca.qc.cgodin.projetfinalandroid.util.Constants.BASE_URL
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)

//                .addInterceptor { chain ->
//                    val request: Request =
//                        chain.request().newBuilder().addHeader("Authorization", SharedPref.USER_TOKEN).build()
//                    chain.proceed(request)
//                }
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(Api::class.java)
        }
    }
}