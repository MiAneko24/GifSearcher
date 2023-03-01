package com.example.studyapp.ui.main.recycler_elems

import android.graphics.ImageDecoder
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.studyapp.R
import com.example.studyapp.ui.main.models.GifModelUI

class GifViewHolder(
    itemView: View
): RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.gif_view)

    fun bind(model: GifModelUI) {
        Glide.with(imageView).load(model.gif).into(imageView)
    }
}