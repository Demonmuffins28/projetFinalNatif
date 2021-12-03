package ca.qc.cgodin.projetfinalandroid

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var api: Api

    fun getTokenService(): Api {
        if (!::api.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        //        .client(okhttpClient(context))


            api = retrofit.create(Api::class.java)
        }

        return api
    }
//
//    private fun okhttpClient(context: Context): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(AuthInterceptor(context))
//            .build()
//    }
}