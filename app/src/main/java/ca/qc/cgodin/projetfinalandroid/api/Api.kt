package ca.qc.cgodin.projetfinalandroid.api

import ca.qc.cgodin.projetfinalandroid.models.LoginResponse
import ca.qc.cgodin.projetfinalandroid.models.publications.PublicationsResponse
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.Utilisateur
import ca.qc.cgodin.projetfinalandroid.util.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @POST(Constants.LOGIN_URL)
    fun login(
        @Header("Authorization") authorization: String
    ): Call<LoginResponse>

//    @GET(Constants.USER_URL)
//    suspend fun getAllUsers(
//        @Header("Authorization") token : String
//    ) : Response<UtilisateursResponse>

    @GET(Constants.USER_URL + "/{id}")
    suspend fun getUser(
        @Header("Authorization") token : String,
        @Path("id") userId : Int
    ) : Response<Utilisateur>

    @GET(Constants.POSTS_URL)
    suspend fun getAllPost(
        @Header("Authorization") token : String,
        @Query("page") page : Int
    ) : Response<PublicationsResponse>

    @GET(Constants.POSTS_URL)
    suspend fun getPost(
        @Header("Authorization") token : String,
        @Query("postId") postId : Int
    ) : Response<PublicationsResponse>

    @FormUrlEncoded
    @POST(Constants.POSTS_URL)
    suspend fun addPost(
        @Header("Authorization") token : String,
        @Field("corps") body : String
    )

    @POST(Constants.FOLLOW_URL + "/{id}")
    suspend fun abonner(
        @Header("Authorization") token : String,
        @Path("id") userId : Int
    )

    @DELETE(Constants.FOLLOW_URL + "/{id}")
    suspend fun desabonner(
        @Header("Authorization") token : String,
        @Path("id") userId: Int
    )
}