package ca.qc.cgodin.projetfinalandroid.repository

import ca.qc.cgodin.projetfinalandroid.api.RetrofitInstance
import ca.qc.cgodin.projetfinalandroid.db.AppDatabase

// Call request to api
class UtilisateursRepository(
    //val db: AppDatabase
) {
    suspend fun getAllUtilisateurs(token: String) =
        // Remove token from request
        RetrofitInstance.api.getAllUsers(token)
}