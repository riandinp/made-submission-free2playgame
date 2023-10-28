package com.riandinp.freegamesdb.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.riandinp.freegamesdb.R

fun getPublisherDeveloper(publisher: String, developer: String): String {
    return if (publisher == developer) publisher
    else "${publisher}/${developer}"
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.placeholder)
        .into(this)
}