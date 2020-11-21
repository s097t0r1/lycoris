package com.s097t0r1.lycoris.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.s097t0r1.lycoris.R
import com.s097t0r1.lycoris.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            detailsViewModel = viewModel
        }

        viewModel.getPhoto(args.id)

        initErrorHandlers()

        return binding.root
    }


    private fun initErrorHandlers() {
        viewModel.errorLoadingData.observe(viewLifecycleOwner, { errorLoadingData ->
            if(errorLoadingData) {
                Toast.makeText(requireContext(), "Check internet connection", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.errorMarkingFavorite.observe(viewLifecycleOwner, { errorMarkingFavorite ->
            if(errorMarkingFavorite) {
                Toast.makeText(requireContext(), "Error marking favorite", Toast.LENGTH_SHORT).show()
                viewModel.errorMarkingFavoriteEventComplete()
            }
        })
    }



}