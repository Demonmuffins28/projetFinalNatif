package ca.qc.cgodin.projetfinalandroid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.qc.cgodin.projetfinalandroid.repository.UtilisateursRepository
import java.lang.IllegalArgumentException

class UtilisateursViewModelProviderFactory(
    private val utilisateursRepository: UtilisateursRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(UtilisateursViewModel::class.java)) {
            UtilisateursViewModel(utilisateursRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}