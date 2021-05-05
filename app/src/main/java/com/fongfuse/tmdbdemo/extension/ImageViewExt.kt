package com.fongfuse.tmdbdemo.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fongfuse.tmdbdemo.util.ConfigUtil

fun ImageView.setImageUrl(imgResource : Any?) {
    Glide.with(context)
        .load(ConfigUtil.IMAGE_URL + imgResource)
//        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}