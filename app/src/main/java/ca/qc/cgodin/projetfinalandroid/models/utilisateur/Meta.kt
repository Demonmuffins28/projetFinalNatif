package ca.qc.cgodin.projetfinalandroid.models.utilisateur


import com.google.gson.annotations.SerializedName

data class Meta(
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("total_items")
    val totalItems: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)