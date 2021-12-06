package ca.qc.cgodin.projetfinalandroid.repository

import ca.qc.cgodin.projetfinalandroid.api.RetrofitInstance
import ca.qc.cgodin.projetfinalandroid.db.AppDatabase

// Call request to api
class AppRepository(
    //val db: AppDatabase
) {
    suspend fun getAllPublications(token: String) =
        RetrofitInstance.api.getAllPost(token)

    suspend fun getUtilisateur(token: String, utilId: Int) =
        RetrofitInstance.api.getUser(token, utilId)

    suspend fun setAbonner(token: String, utilId: Int) =
        RetrofitInstance.api.abonner(token, utilId)

    suspend fun setDesabonner(token: String, utilId: Int) =
        RetrofitInstance.api.desabonner(token, utilId)

    suspend fun addPublication(token: String, body: String) =
        RetrofitInstance.api.addPost(token, body)
}