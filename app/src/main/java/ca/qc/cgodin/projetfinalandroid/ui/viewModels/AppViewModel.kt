package ca.qc.cgodin.projetfinalandroid.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cgodin.projetfinalandroid.models.publications.PublicationsResponse
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.Utilisateur
import ca.qc.cgodin.projetfinalandroid.repository.AppRepository
import ca.qc.cgodin.projetfinalandroid.util.Resource
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import com.bumptech.glide.util.Util
import kotlinx.coroutines.launch
import retrofit2.Response

// Call method from repository to make request to api & handle responses
class AppViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    val utilisateur: MutableLiveData<Resource<Utilisateur>> = MutableLiveData()

    val publications: MutableLiveData<Resource<PublicationsResponse>> = MutableLiveData()
    var pages = 1

    init {}

    fun getUtilisateur(token: String, utilId: Int) = viewModelScope.launch {
        // Loading state
        utilisateur.postValue(Resource.Loading())
        val response = appRepository.getUtilisateur(token, utilId)
        // Error / success state
        utilisateur.postValue(handleUtilisateurResponse(response))
    }

    fun getPublications(token: String) = viewModelScope.launch {
        // Loading state
        publications.postValue(Resource.Loading())
        val response = appRepository.getAllPublications(token)
        // Error / success state
        publications.postValue(handlePublicationsResponse(response))
    }

    fun setAbonner(token: String, utilId: Int) = viewModelScope.launch {
        appRepository.setAbonner(token, utilId)
    }

    fun setDesabonner(token: String, utilId: Int) = viewModelScope.launch {
        appRepository.setDesabonner(token, utilId)
    }

    fun addPublication(token: String, body: String) = viewModelScope.launch {
        appRepository.addPublication(token, body)
    }

    private fun handleUtilisateurResponse(response: Response<Utilisateur>) : Resource<Utilisateur>? {
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