package ca.qc.cgodin.projetfinalandroid.repository

import ca.qc.cgodin.projetfinalandroid.api.RetrofitInstance
import ca.qc.cgodin.projetfinalandroid.db.AppDatabase

// Call request to api
class AppRepository(
    val db: AppDatabase
) {
    suspend fun getAllUtilisateurs() =
        // Remove token from request
        RetrofitInstance.api.getAllUsers()

    suspend fun getAllPublications(token: String) =
        RetrofitInstance.api.getAllPost(token)

    suspend fun getUtilisateur(utilId: Int) =
        RetrofitInstance.api.getUser(utilId)
}