package ca.qc.cgodin.projetfinalandroid.response


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "publications"
)
data class PublicationResponse(
    val corps: String?,
    val horodatage: String?,
    @PrimaryKey
    val id: Int?,
    @SerializedName("utilisateur_id")
    val utilisateurId: Int?
)