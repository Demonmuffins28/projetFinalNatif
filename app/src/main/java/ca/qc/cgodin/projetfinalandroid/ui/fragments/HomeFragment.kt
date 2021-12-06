package ca.qc.cgodin.projetfinalandroid.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.adapters.PublicationAdapter
import ca.qc.cgodin.projetfinalandroid.adapters.UtilisateurAdapter
import ca.qc.cgodin.projetfinalandroid.databinding.FragmentHomeBinding
import ca.qc.cgodin.projetfinalandroid.ui.AppActivity
import ca.qc.cgodin.projetfinalandroid.ui.viewModels.AppViewModel
import ca.qc.cgodin.projetfinalandroid.util.Resource
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import com.bumptech.glide.Glide

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: AppViewModel
    lateinit var publicationAdapter: PublicationAdapter

    val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as AppActivity).viewModel
        setupRecyclerView()

        var token = "Bearer ${SharedPref.read(SharedPref.USER_TOKEN, null)}"
        var utilId = SharedPref.read(SharedPref.USER_ID, 0)

        viewModel.getPublications(token)

        publicationAdapter.setAppViewModel(viewModel)

        getUtilisateur(token, utilId)

        // subscribe to see every change
        viewModel.publications.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { appResponse ->
                        publicationAdapter.differ.submitList(appResponse.publications)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        // send to login if error of unauthorized
                        findNavController().navigate(R.id.action_homeFragment_to_loginActivity)
                        Log.e(TAG, "Une erreur est survenue: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    fun getUtilisateur(token: String, utilId: Int) {
        viewModel.getUtilisateur(token, utilId)
        // subscribe to see every change
        viewModel.utilisateur.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { appResponse ->
                        publicationAdapter.setAbonner(appResponse.partisans)
                        binding.tvHomeBonjourUtil.text = "Bonjour, ${appResponse.nom}!"
                        Glide.with(this).asBitmap().load(appResponse.avatar).into(binding.ivHomeUtil)
                        binding.btnPublier.setOnClickListener {
                            viewModel.addPublication(token, binding.tvPublication.text.toString())
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        if (message == "UNAUTHORIZED")
                        // send to login if error of unauthorized
                            findNavController().navigate(R.id.action_homeFragment_to_loginActivity)
                        Log.e(TAG, "Une erreur est survenue: $message")
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

    private fun setupRecyclerView() {
        publicationAdapter = PublicationAdapter()
        binding.rvHome.apply {
            adapter = publicationAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}