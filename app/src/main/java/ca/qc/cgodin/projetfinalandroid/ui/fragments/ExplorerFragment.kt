package ca.qc.cgodin.projetfinalandroid.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.adapters.PublicationAdapter
import ca.qc.cgodin.projetfinalandroid.adapters.UtilisateurAdapter
import ca.qc.cgodin.projetfinalandroid.databinding.FragmentExplorerBinding
import ca.qc.cgodin.projetfinalandroid.ui.AppActivity
import ca.qc.cgodin.projetfinalandroid.ui.viewModels.AppViewModel
import ca.qc.cgodin.projetfinalandroid.util.Constants
import ca.qc.cgodin.projetfinalandroid.util.Resource
import ca.qc.cgodin.projetfinalandroid.util.SharedPref

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

        var token = "Bearer ${SharedPref.read(SharedPref.USER_TOKEN, null)}"

        viewModel.getPublications(token)

        // subscribe to see every change
        viewModel.publications.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { appResponse ->
                        publicationAdapter.differ.submitList(appResponse.publications?.toList())
                        val totalPages = appResponse.meta?.totalPages
                        isLastPage = viewModel.publicationPage == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        if (message == "UNAUTHORIZED")
                            // send to login if error of unauthorized
                            findNavController().navigate(R.id.action_explorerFragment_to_loginActivity)
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
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                var token = "Bearer ${SharedPref.read(SharedPref.USER_TOKEN, null)}"
                viewModel.getPublications(token)
                isScrolling = false
            }
        }
    }

    private fun setupRecyclerView() {
        publicationAdapter = PublicationAdapter()
        binding.rvHome.apply {
            adapter = publicationAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@ExplorerFragment.scrollListener)
        }
    }
}