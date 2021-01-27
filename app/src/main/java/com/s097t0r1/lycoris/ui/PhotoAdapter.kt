package com.s097t0r1.lycoris.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.s097t0r1.lycoris.R
import com.s097t0r1.lycoris.data.Photo
import com.s097t0r1.lycoris.databinding.ItemPhotoBinding

class PhotoAdapter(private val onClick: ItemClickListener) : ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(PhotoItemDiffCallback()) {

    class PhotoViewHolder private constructor(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo, onClick: ItemClickListener) {
            binding.photo = photo
            binding.clickListener = onClick
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val binding: ItemPhotoBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_photo, parent, false)
                return PhotoViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}

class PagingPhotoAdapter(private val clickListener: ItemClickListener) : PagingDataAdapter<Photo, PagingPhotoAdapter.PagingPhotoViewHolder>(PhotoItemDiffCallback()) {

    class PagingPhotoViewHolder private constructor(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo?, clickListener: ItemClickListener) {
            binding.photo = photo
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PagingPhotoViewHolder {
                val binding: ItemPhotoBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_photo, parent, false)
                return PagingPhotoViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingPhotoViewHolder {
        return PagingPhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PagingPhotoViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

}




class PhotoItemDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return (oldItem.description == newItem.description) && (oldItem.imageUrl == newItem.imageUrl)
    }

}

fun interface ItemClickListener {
    fun onClick(id: String)
}