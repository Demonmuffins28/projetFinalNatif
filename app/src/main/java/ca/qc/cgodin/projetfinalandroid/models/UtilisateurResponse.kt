package ca.qc.cgodin.projetfinalandroid.response


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "utilisateurs"
)
data class UtilisateurResponse(
    @SerializedName("aProposDeMoi")
    val aProposDeMoi: String?,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("courriel")
    val courriel: String?,
    @SerializedName("dernierAcces")
    val dernierAcces: String?,
    @SerializedName("id")
    @PrimaryKey
    var id: Int? = null,
    @SerializedName("nom")
    val nom: String?,
    @SerializedName("partisans")
    val partisans: List<Any>?,
    @SerializedName("publications")
    val publications: List<Int>?
)