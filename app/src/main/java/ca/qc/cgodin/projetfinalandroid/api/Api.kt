package ca.qc.cgodin.projetfinalandroid.api

import ca.qc.cgodin.projetfinalandroid.models.LoginResponse
import ca.qc.cgodin.projetfinalandroid.models.publications.PublicationsResponse
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.UtilisateursResponse
import ca.qc.cgodin.projetfinalandroid.util.Constants
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
    suspend fun getAllUsers(
        @Header("Authorization") authorization: String
    ) : Response<UtilisateursResponse>

    @GET(Constants.USER_URL)
    suspend fun getUser(
        @Query("userId") userId : Int,
        @Header("Authorization") authorization: String
    ) : Response<UtilisateursResponse>

    @GET(Constants.POSTS_URL)
    suspend fun getAllPost(
        @Query("token") token : String
    ) : Response<PublicationsResponse>

    @GET(Constants.POSTS_URL)
    suspend fun getPost(
        @Query("token") token : String,
        @Query("postId") postId : Int
    ) : Response<PublicationsResponse>
}