package com.s097t0r1.lycoris.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.s097t0r1.lycoris.R
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



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        initErrorHandlers()
        initListeners()
    }

    private fun initErrorHandlers() {
        viewModel.errorMessage.observe(viewLifecycleOwner, { errorMessage ->
            errorMessage?.let {
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
                viewModel.errorEventComplete()
            }
        })
    }

    private fun initRecyclerView() {
        val adapter = PhotoAdapter {
            findNavController().navigate(FeedFragmentDirections.actionNavigationFeedToDetailsFragment(it))
        }
        val layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        viewModel.photos.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        binding.recyclerViewPhotosList.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }
    }

    private fun initListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPhotos()
        }
    }

}