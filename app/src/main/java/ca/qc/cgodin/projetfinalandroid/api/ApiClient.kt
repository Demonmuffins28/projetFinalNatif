package ca.qc.cgodin.projetfinalandroid.api

import ca.qc.cgodin.projetfinalandroid.util.Constants
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

            api = retrofit.create(Api::class.java)
        }

        return api
    }
}