package ca.qc.cgodin.projetfinalandroid.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.qc.cgodin.projetfinalandroid.repository.AppRepository

class AppViewModelProviderFactory(
    private val appRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel(appRepository) as T
    }
}