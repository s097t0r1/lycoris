package com.s097t0r1.lycoris.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.s097t0r1.lycoris.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            detailsViewModel = viewModel
        }

        viewModel.getPhoto(args.id)

        return binding.root
    }


}