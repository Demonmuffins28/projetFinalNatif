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
import ca.qc.cgodin.projetfinalandroid.databinding.FragmentHomeBinding
import ca.qc.cgodin.projetfinalandroid.ui.AppActivity
import ca.qc.cgodin.projetfinalandroid.ui.viewModels.AppViewModel
import ca.qc.cgodin.projetfinalandroid.util.Constants.QUERY_PAGE_SIZE
import ca.qc.cgodin.projetfinalandroid.util.Resource
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import ca.qc.cgodin.projetfinalandroid.util.SocketHandler
import com.bumptech.glide.Glide
import io.socket.client.Socket

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
        SocketHandler.setSocket()
        val mSocket = SocketHandler.getSocket()
        SocketHandler.establishConnection()

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
                            findNavController().navigate(R.id.action_homeFragment_to_loginActivity)
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
                            if (binding.tvPublication.text.toString() != "")
                                viewModel.addPublication(token, binding.tvPublication.text.toString())
                            else
                                Toast.makeText(activity, "Ajouter quelque chose à publier.", Toast.LENGTH_LONG).show()
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
        SocketHandler.closeConnection()
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
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
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
            addOnScrollListener(this@HomeFragment.scrollListener)
        }
    }
}