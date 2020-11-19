package com.s097t0r1.lycoris.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.s097t0r1.lycoris.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val feedViewModel: FeedViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_feed, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        feedViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        feedViewModel.getPhotos()
        return root
    }
}