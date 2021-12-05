package ca.qc.cgodin.projetfinalandroid.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ca.qc.cgodin.projetfinalandroid.models.publications.Publication

@Dao
interface PublicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(publication: Publication): Int

    @Query("SELECT * FROM publications")
    fun getAllPublications(): LiveData<List<Publication>>

    @Delete
    suspend fun deletePublication(publication: Publication)
}