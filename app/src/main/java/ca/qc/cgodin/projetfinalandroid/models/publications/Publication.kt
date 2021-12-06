package ca.qc.cgodin.projetfinalandroid.models.publications


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "publications"
)
data class Publication(
    val corps: String?,
    val horodatage: String?,
    @PrimaryKey
    val id: Int?,
    @SerializedName("utilisateur_id")
    val utilisateurId: Int,
    @SerializedName("nom")
    val nom: String?,
    @SerializedName("avatar")
    val avatar: String?
): Serializable