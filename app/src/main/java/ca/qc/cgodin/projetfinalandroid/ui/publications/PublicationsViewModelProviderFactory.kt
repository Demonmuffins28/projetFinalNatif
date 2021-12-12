package ca.qc.cgodin.projetfinalandroid.ui.publications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.qc.cgodin.projetfinalandroid.repository.AppRepository

class PublicationsViewModelProviderFactory(
    private val publicationRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PublicationViewModel(publicationRepository) as T
    }
}