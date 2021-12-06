package ca.qc.cgodin.projetfinalandroid.models.utilisateur


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "utilisateurs"
)
data class Utilisateur(
    @SerializedName("a_propos_de_moi")
    val aProposDeMoi: String?,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("courriel")
    val courriel: String?,
    @SerializedName("dernier_acces")
    val dernierAcces: String?,
    @PrimaryKey
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("nom")
    val nom: String?,
    @SerializedName("partisans")
    val partisans: List<Int>?,
    @SerializedName("publications")
    val publications: List<Int>?
): Serializable