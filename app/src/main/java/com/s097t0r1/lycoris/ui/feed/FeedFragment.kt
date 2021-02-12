package com.s097t0r1.lycoris.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.s097t0r1.lycoris.databinding.FragmentFeedBinding
import com.s097t0r1.lycoris.ui.PagingPhotoAdapter
import com.s097t0r1.lycoris.ui.PhotoLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var binding: FragmentFeedBinding


    private var gettingPhotoJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            feedViewModel = viewModel
        }

        setupRecyclerView()
        setupListeners()

        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = PagingPhotoAdapter { id ->
            findNavController().navigate(FeedFragmentDirections.actionNavigationFeedToDetailsFragment(id))
        }

        adapter.addLoadStateListener { loadState ->
            binding.swipeRefreshLayout.isRefreshing = loadState.refresh is LoadState.Loading
            binding.recyclerViewPhotosList.isVisible = loadState.refresh !is LoadState.Loading && loadState.refresh !is LoadState.Error
            binding.textViewErrorMessage.isVisible = loadState.refresh is LoadState.Error
        }

        val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        binding.recyclerViewPhotosList.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        initPhotoStream()

    }

    private fun initPhotoStream() {
        gettingPhotoJob?.cancel()
        gettingPhotoJob = lifecycleScope.launch {
            viewModel.getPhotos().collect {
                (binding.recyclerViewPhotosList.adapter as PagingPhotoAdapter).submitData(it)
            }
        }
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            initPhotoStream()
        }
    }

}