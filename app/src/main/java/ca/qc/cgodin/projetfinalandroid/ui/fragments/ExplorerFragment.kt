package ca.qc.cgodin.projetfinalandroid.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.adapters.PublicationAdapter
import ca.qc.cgodin.projetfinalandroid.databinding.FragmentExplorerBinding
import ca.qc.cgodin.projetfinalandroid.ui.AppActivity
import ca.qc.cgodin.projetfinalandroid.ui.viewModels.AppViewModel
import ca.qc.cgodin.projetfinalandroid.util.Resource

class ExplorerFragment : Fragment(R.layout.fragment_explorer) {
    private var _binding: FragmentExplorerBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: AppViewModel
    lateinit var publicationAdapter: PublicationAdapter

    val TAG = "ExplorerFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExplorerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as AppActivity).viewModel
        setupRecyclerView()

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
                        findNavController().navigate(R.id.action_explorerFragment_to_loginActivity)
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