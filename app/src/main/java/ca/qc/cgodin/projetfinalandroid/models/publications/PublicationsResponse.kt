package ca.qc.cgodin.projetfinalandroid.models.publications


import com.google.gson.annotations.SerializedName

data class PublicationsResponse(
    @SerializedName("items")
    val publications: MutableList<Publication>,
    @SerializedName("_links")
    val links: Links?,
    @SerializedName("_meta")
    val meta: Meta?
)