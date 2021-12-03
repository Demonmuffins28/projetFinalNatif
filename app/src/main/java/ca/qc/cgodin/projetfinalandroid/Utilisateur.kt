package ca.qc.cgodin.projetfinalandroid


import com.google.gson.annotations.SerializedName

data class Utilisateur(
    @SerializedName("aProposDeMoi")
    val aProposDeMoi: String?,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("courriel")
    val courriel: String?,
    @SerializedName("dernierAcces")
    val dernierAcces: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("nom")
    val nom: String?,
    @SerializedName("partisans")
    val partisans: List<Any>?,
    @SerializedName("publications")
    val publications: List<Int>?
)