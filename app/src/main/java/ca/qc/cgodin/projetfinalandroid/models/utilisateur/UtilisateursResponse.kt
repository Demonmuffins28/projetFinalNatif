package ca.qc.cgodin.projetfinalandroid.models.utilisateur


import com.google.gson.annotations.SerializedName

data class UtilisateursResponse(
    @SerializedName("items")
    val utilisateurs: List<Utilisateur>,
    @SerializedName("_links")
    val links: Links?,
    @SerializedName("_meta")
    val meta: Meta?
)