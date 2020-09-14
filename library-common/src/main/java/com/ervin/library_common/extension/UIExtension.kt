package com.ervin.library_common.extension

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun <T : RecyclerView.ViewHolder> T.onClick(action: (view: View, position: Int) -> Unit): T {
    itemView.setOnClickListener {
        action(it, adapterPosition)
    }
    return this
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setVisible() {
    visibility = View.VISIBLE
}