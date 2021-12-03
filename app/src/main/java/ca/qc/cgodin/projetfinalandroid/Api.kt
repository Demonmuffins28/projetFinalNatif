package ca.qc.cgodin.projetfinalandroid

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {
    @POST(Constants.LOGIN_URL)
    fun login(@Header("Authorization") authorization: String): Call<LoginResponse>
}