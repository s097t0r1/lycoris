package com.s097t0r1.lycoris.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
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
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val adapter = PhotoAdapter()
        val layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        viewModel.photos.observe(viewLifecycleOwner, {
            Log.d("FeedFragment", it.size.toString())
            adapter.submitList(it)
        })

        binding.recyclerViewPhotosList.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }
    }

}