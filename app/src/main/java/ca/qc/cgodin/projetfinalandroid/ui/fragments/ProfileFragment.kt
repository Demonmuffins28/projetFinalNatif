package ca.qc.cgodin.projetfinalandroid.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.adapters.AppAdapter
import ca.qc.cgodin.projetfinalandroid.databinding.FragmentProfileBinding
import ca.qc.cgodin.projetfinalandroid.repository.UtilisateursRepository
import ca.qc.cgodin.projetfinalandroid.ui.AppActivity
import ca.qc.cgodin.projetfinalandroid.ui.UtilisateursViewModel
import ca.qc.cgodin.projetfinalandroid.ui.UtilisateursViewModelProviderFactory
import ca.qc.cgodin.projetfinalandroid.util.Resource

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: UtilisateursViewModel
    lateinit var appAdapter: AppAdapter

    val TAG = "ProfileFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnDisconnect.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_loginActivity)
        }

        val utilisateursRepository = UtilisateursRepository()
        val viewModelProviderFactory = UtilisateursViewModelProviderFactory(utilisateursRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(UtilisateursViewModel::class.java)

        appAdapter = AppAdapter()
        binding.rvProfile.adapter = appAdapter

        viewModel.utilisateurs.observe(
            viewLifecycleOwner, Observer { response ->
                appAdapter.setUtilisateurs(response.utilisateurs)
            }
        )
        //setupRecyclerView()

        // subscribe to see every change
//        viewModel.utilisateurs.observe(viewLifecycleOwner, Observer { response ->
//            when(response) {
//                is Resource.Success -> {
//                    hideProgressBar()
//                    response.data?.let { appResponse ->
//                        appAdapter.differ.submitList(appResponse.utilisateurs)
//                    }
//                }
//                is Resource.Error -> {
//                    hideProgressBar()
//                    response.message?.let { message ->
//                        Log.e(TAG, "Une erreur est survenue: $message")
//                    }
//                }
//                is Resource.Loading -> {
//                    showProgressBar()
//                }
//            }
//        })
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

    private fun setupRecyclerView() {
        appAdapter = AppAdapter()
        binding.rvProfile.apply {
            adapter = appAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}