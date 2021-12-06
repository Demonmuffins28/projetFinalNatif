package ca.qc.cgodin.projetfinalandroid.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cgodin.projetfinalandroid.models.publications.PublicationsResponse
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.Utilisateur
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.UtilisateursResponse
import ca.qc.cgodin.projetfinalandroid.repository.AppRepository
import ca.qc.cgodin.projetfinalandroid.util.Resource
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import kotlinx.coroutines.launch
import retrofit2.Response

// Call method from repository to make request to api & handle responses
class AppViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    val utilisateurs: MutableLiveData<Resource<UtilisateursResponse>> = MutableLiveData()
    val utilisateur: MutableLiveData<Response<Utilisateur>> = MutableLiveData()

    val publications: MutableLiveData<Resource<PublicationsResponse>> = MutableLiveData()
    var pages = 1

    init {
        var token = SharedPref.read(SharedPref.USER_TOKEN, null)
        var utilId = SharedPref.read(SharedPref.USER_ID, 0)
        if (token != null) {
            getUtilisateurs()
            getPublications("Bearer $token")
        }
    }

    fun getUtilisateurs() = viewModelScope.launch {
        // Loading state
        utilisateurs.postValue(Resource.Loading())
        val response = appRepository.getAllUtilisateurs()
        // Error / success state
        utilisateurs.postValue(handleUtilisateursResponse(response))
    }

    fun getPublications(token: String) = viewModelScope.launch {
        // Loading state
        publications.postValue(Resource.Loading())
        val response = appRepository.getAllPublications(token)
        // Error / success state
        publications.postValue(handlePublicationsResponse(response))
    }

    private fun handleUtilisateursResponse(response: Response<UtilisateursResponse>) : Resource<UtilisateursResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlePublicationsResponse(response: Response<PublicationsResponse>) : Resource<PublicationsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}