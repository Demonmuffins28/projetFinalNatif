package ca.qc.cgodin.projetfinalandroid.ui.publications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cgodin.projetfinalandroid.models.publications.PublicationsResponse
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.UtilisateursResponse
import ca.qc.cgodin.projetfinalandroid.repository.AppRepository
import ca.qc.cgodin.projetfinalandroid.util.Resource
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import kotlinx.coroutines.launch
import retrofit2.Response

// Call method from repository to make request to api & handle responses
class PublicationViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    val publications: MutableLiveData<Resource<PublicationsResponse>> = MutableLiveData()
    var pages = 1

    init {
        var token = SharedPref.read(SharedPref.USER_TOKEN, null)
        if (token != null) {
            getPublications(token)
        }
    }

    fun getPublications(token: String) = viewModelScope.launch {
        // Loading state
        publications.postValue(Resource.Loading())
        val response = appRepository.getAllPublications(token)
        // Error / success state
        publications.postValue(handlePublicationsResponse(response))
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
