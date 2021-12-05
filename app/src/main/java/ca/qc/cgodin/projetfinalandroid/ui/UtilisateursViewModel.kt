package ca.qc.cgodin.projetfinalandroid.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.UtilisateursResponse
import ca.qc.cgodin.projetfinalandroid.repository.UtilisateursRepository
import ca.qc.cgodin.projetfinalandroid.util.Resource
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

// Call method from repository to make request to api & handle responses
class UtilisateursViewModel(
    private val utilisateursRepository: UtilisateursRepository
) : ViewModel() {

    val utilisateurs: MutableLiveData<UtilisateursResponse> = MutableLiveData()

    init {
        var token = SharedPref.read(SharedPref.USER_TOKEN, null)
        if (token != null) {
            getUtilisateurs(token)
        }
    }

    fun getUtilisateurs(token: String) = viewModelScope.launch {
        try {
            val response = utilisateursRepository.getAllUtilisateurs(token)
            utilisateurs.postValue(response.body())
        } catch (e:Exception){}
//        // Loading state
//        utilisateurs.postValue(Resource.Loading())
//        val response = utilisateursRepository.getAllUtilisateurs(token)
//        // Error / success state
//        utilisateurs.postValue(handleUtilisateursResponse(response))
    }

    private fun handleUtilisateursResponse(response: Response<UtilisateursResponse>) : Resource<UtilisateursResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}