package ca.qc.cgodin.projetfinalandroid.models.utilisateur


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "utilisateurs"
)
data class Utilisateur(
    @SerializedName("a_propos_de_moi")
    val aProposDeMoi: String?,
    val avatar: String?,
    val courriel: String?,
    @SerializedName("dernier_acces")
    val dernierAcces: String?,
    @PrimaryKey
    val id: Int? = null,
    val nom: String?,
    val partisans: List<Int>?,
    val publications: List<Int>?
)