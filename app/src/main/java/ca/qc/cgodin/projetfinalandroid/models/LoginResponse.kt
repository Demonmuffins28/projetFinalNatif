package ca.qc.cgodin.projetfinalandroid.models


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("jeton")
    var jeton: String?,
    @SerializedName("user_id")
    var idUtil: Int?,
    @SerializedName("erreur")
    var erreur: String?
)