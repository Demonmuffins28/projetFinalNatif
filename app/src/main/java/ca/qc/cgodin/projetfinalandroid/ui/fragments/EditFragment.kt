package ca.qc.cgodin.projetfinalandroid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.ui.AppActivity
import ca.qc.cgodin.projetfinalandroid.ui.UtilisateursViewModel

class EditFragment : Fragment(R.layout.fragment_explorer) {

    lateinit var viewModel: UtilisateursViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as AppActivity).viewModel
    }
}