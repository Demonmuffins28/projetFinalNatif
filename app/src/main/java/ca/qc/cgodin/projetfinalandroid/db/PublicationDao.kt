package ca.qc.cgodin.projetfinalandroid.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ca.qc.cgodin.projetfinalandroid.models.publications.Publication

@Dao
interface PublicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(publication: Publication): Long

    @Query("SELECT * FROM publications")
    fun getAllPublications(): LiveData<List<Publication>>

    @Delete
    fun deletePublication(publication: Publication)
}