package ca.qc.cgodin.projetfinalandroid.ui.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cgodin.projetfinalandroid.models.publications.PublicationsResponse
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.Utilisateur
import ca.qc.cgodin.projetfinalandroid.repository.AppRepository
import ca.qc.cgodin.projetfinalandroid.util.AppApplication
import ca.qc.cgodin.projetfinalandroid.util.Resource
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import com.bumptech.glide.util.Util
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.lang.Error

// Call method from repository to make request to api & handle responses
class AppViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    val utilisateur: MutableLiveData<Resource<Utilisateur>> = MutableLiveData()

    val publications: MutableLiveData<Resource<PublicationsResponse>> = MutableLiveData()
    var publicationPage = 1
    var publicationsResponse: PublicationsResponse? = null

    init {}

    fun getUtilisateur(token: String, utilId: Int) = viewModelScope.launch {
        safeUtilisateursCall(token, utilId)
    }

    fun getPublications(token: String) = viewModelScope.launch {
        safePublicationsCall(token)
    }

    fun setAbonner(token: String, utilId: Int) = viewModelScope.launch {
        try {
            if (hasInternetConnection()) {
                appRepository.setAbonner(token, utilId)
            }
        } catch (t: Throwable) {}
    }

    fun setDesabonner(token: String, utilId: Int) = viewModelScope.launch {
        try {
            if (hasInternetConnection()) {
                appRepository.setDesabonner(token, utilId)
            }
        } catch (t: Throwable) {}
    }

    fun addPublication(token: String, body: String) = viewModelScope.launch {
        try {
            if (hasInternetConnection()) {
                appRepository.addPublication(token, body)
            }
        } catch (t: Throwable) {}
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
                publicationPage++
                if (publicationsResponse == null) {
                    publicationsResponse = resultResponse
                } else {
                    val oldPublications = publicationsResponse?.publications
                    val newPublications = resultResponse.publications
                    oldPublications?.addAll(newPublications)
                }
                return Resource.Success(publicationsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeUtilisateursCall(token: String, utilId: Int) {
        utilisateur.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = appRepository.getUtilisateur(token, utilId)
                utilisateur.postValue(handleUtilisateurResponse(response))
            } else {
                utilisateur.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> utilisateur.postValue(Resource.Error("Network Failure"))
                else -> utilisateur.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safePublicationsCall(token: String) {
        publications.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = appRepository.getAllPublications(token, publicationPage)
                publications.postValue(handlePublicationsResponse(response))
            } else {
                publications.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> publications.postValue(Resource.Error("Network Failure"))
                else -> publications.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<AppApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}