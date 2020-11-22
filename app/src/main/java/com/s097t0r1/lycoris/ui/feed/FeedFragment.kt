package com.s097t0r1.lycoris.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.s097t0r1.lycoris.databinding.FragmentFeedBinding
import com.s097t0r1.lycoris.ui.PhotoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            feedViewModel = viewModel
        }

        setupRecyclerView()
        setupListeners()

        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = PhotoAdapter() { id ->
            findNavController().navigate(FeedFragmentDirections.actionNavigationFeedToDetailsFragment(id))
        }

        val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        binding.recyclerViewPhotosList.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        viewModel.photos.observe(viewLifecycleOwner, { listPhotos ->
            adapter.submitList(listPhotos)
        })
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPhotos()
        }
    }

}