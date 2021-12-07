package ca.qc.cgodin.projetfinalandroid.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.adapters.UtilisateurAdapter
import ca.qc.cgodin.projetfinalandroid.databinding.FragmentProfileBinding
import ca.qc.cgodin.projetfinalandroid.ui.AppActivity
import ca.qc.cgodin.projetfinalandroid.ui.viewModels.AppViewModel
import ca.qc.cgodin.projetfinalandroid.util.Resource
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import com.bumptech.glide.Glide

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: AppViewModel
    lateinit var utilisateurAdapter: UtilisateurAdapter

    val TAG = "ProfileFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnDisconnect.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_loginActivity)
        }

        viewModel = (activity as AppActivity).viewModel

        var token = "Bearer ${SharedPref.read(SharedPref.USER_TOKEN, null)}"
        var utilId = SharedPref.read(SharedPref.USER_ID, 0)

        viewModel.getUtilisateur(token, utilId)

        // subscribe to see every change
        viewModel.utilisateur.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { appResponse ->
                        binding.tvProfAbout.text = appResponse.aProposDeMoi
                        binding.tvProflConnexion.text =  getString(R.string.tvDerniereConn, appResponse.dernierAcces)
                        binding.tvProfilPartisans.text = getString(R.string.tvPartisans, appResponse.partisans?.size)
                        Glide.with(this).asBitmap().load(appResponse.avatar).into(binding.ivProfilImage)
                        binding.tvProfilNom.text = appResponse.nom
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        if (message == "UNAUTHORIZED")
                            // send to login if error of unauthorized
                            findNavController().navigate(R.id.action_profileFragment_to_loginActivity)
                        Log.e(TAG, "Une erreur est survenue: $message")
                        Toast.makeText(activity, "Une erreur est survenue: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
}

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
}