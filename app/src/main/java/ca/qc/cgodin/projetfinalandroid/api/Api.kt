package ca.qc.cgodin.projetfinalandroid.api

import ca.qc.cgodin.projetfinalandroid.models.LoginResponse
import ca.qc.cgodin.projetfinalandroid.models.publications.PublicationsResponse
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.UtilisateursResponse
import ca.qc.cgodin.projetfinalandroid.util.Constants
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @POST(Constants.LOGIN_URL)
    fun login(
        @Header("Authorization") authorization: String
    ): Call<LoginResponse>

    @GET(Constants.USER_URL)
    suspend fun getAllUsers() : Response<UtilisateursResponse>

    @GET(Constants.USER_URL)
    suspend fun getUser(
        @Query("userId") userId : Int
    ) : Response<UtilisateursResponse>

    @GET(Constants.POSTS_URL)
    suspend fun getAllPost(
        @Header("Authorization") token : String
    ) : Response<PublicationsResponse>

    @GET(Constants.POSTS_URL)
    suspend fun getPost(
        @Query("postId") postId : Int
    ) : Response<PublicationsResponse>
}