package ca.qc.cgodin.projetfinalandroid.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.Utilisateur

@Dao
interface UtilisateurDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(utilisateur: Utilisateur): Long

    @Query("SELECT * FROM utilisateurs")
    fun getAllUtilisateurs(): LiveData<List<Utilisateur>>

    @Delete
    fun deleteUtilisateur(utilisateur: Utilisateur)
}