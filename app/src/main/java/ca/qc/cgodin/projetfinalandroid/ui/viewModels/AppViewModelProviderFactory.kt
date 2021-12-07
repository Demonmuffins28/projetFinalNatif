package ca.qc.cgodin.projetfinalandroid.ui.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.qc.cgodin.projetfinalandroid.repository.AppRepository

class AppViewModelProviderFactory(
    private val app: Application,
    private val appRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel(app, appRepository) as T
    }
}