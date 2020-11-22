package com.s097t0r1.lycoris.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.s097t0r1.lycoris.databinding.FragmentFavoritesBinding
import com.s097t0r1.lycoris.ui.PhotoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false).apply {
            favoritesViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        setupRecyclerView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPhotos()
    }

    private fun setupRecyclerView() {
        val adapter = PhotoAdapter() { id ->
            findNavController().navigate(FavoritesFragmentDirections.actionNavigationFavoritesToDetailsFragment(id))
        }

        val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        binding.favoritesList.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        viewModel.photos.observe(viewLifecycleOwner, { listPhotos ->
            adapter.submitList(listPhotos)
        })
    }
}