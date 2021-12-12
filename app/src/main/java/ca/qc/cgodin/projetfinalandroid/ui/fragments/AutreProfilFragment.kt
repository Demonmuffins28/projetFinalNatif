package ca.qc.cgodin.projetfinalandroid.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.adapters.PublicationAdapter
import ca.qc.cgodin.projetfinalandroid.adapters.UtilisateurAdapter
import ca.qc.cgodin.projetfinalandroid.databinding.FragmentExplorerBinding
import ca.qc.cgodin.projetfinalandroid.ui.viewModels.AppViewModel

class AutreProfilFragment : Fragment(R.layout.fragment_autre_profil) {
    private var _binding: FragmentExplorerBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: AppViewModel

    val TAG = "AutreProfilFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExplorerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}