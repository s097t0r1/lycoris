package com.s097t0r1.lycoris.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.s097t0r1.lycoris.databinding.ItemLoadStateBinding

class PhotoLoadStateAdapter(
    private val retryFunction: () -> Unit
) : LoadStateAdapter<PhotoLoadStateAdapter.PhotoLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: PhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PhotoLoadStateViewHolder {
        return PhotoLoadStateViewHolder.from(parent, retryFunction)
    }

    class PhotoLoadStateViewHolder private constructor(
        val binding: ItemLoadStateBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.btnRetryButton.isVisible = loadState is LoadState.Error
            binding.cpiProgressBar.isVisible = loadState is LoadState.Loading
        }

        companion object {
            fun from(parent: ViewGroup, retry: () -> Unit): PhotoLoadStateViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemLoadStateBinding.inflate(inflater, parent, false)

                return PhotoLoadStateViewHolder(binding, retry = retry)
            }
        }
    }

}