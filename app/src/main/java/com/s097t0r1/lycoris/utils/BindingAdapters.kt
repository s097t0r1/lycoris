package com.s097t0r1.lycoris.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.s097t0r1.lycoris.R

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(it)
            .placeholder(R.drawable.ic_baseline_photo_24)
            .error(R.drawable.ic_baseline_error_outline_24)
            .centerCrop()
            .into(this)
    }
}


@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.setIsRefreshing(dataLoading: Boolean?) {
    dataLoading?.let {
        isRefreshing = dataLoading
    }
}

