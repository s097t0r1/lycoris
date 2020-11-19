package com.s097t0r1.lycoris.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(it)
            .centerCrop()
            .into(this)
    }


}