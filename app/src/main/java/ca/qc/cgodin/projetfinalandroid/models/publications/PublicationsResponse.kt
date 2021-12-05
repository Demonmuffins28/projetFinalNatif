package ca.qc.cgodin.projetfinalandroid.models.publications


import com.google.gson.annotations.SerializedName

data class PublicationsResponse(
    val publications: List<Publication>?,
    @SerializedName("_links")
    val links: Links?,
    @SerializedName("_meta")
    val meta: Meta?
)