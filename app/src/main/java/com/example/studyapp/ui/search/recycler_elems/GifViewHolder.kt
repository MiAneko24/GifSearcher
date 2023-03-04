package com.example.studyapp.ui.search.recycler_elems

import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.studyapp.R
import com.example.studyapp.ui.models.GifModelUI
import com.google.android.material.color.MaterialColors

class GifViewHolder(
    itemView: View
): RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.gif_view)

    fun bind(model: GifModelUI, listener: OnClickListener) {
        Glide.with(imageView).load(model.gif).into(imageView)
        imageView.setOnClickListener(listener)

    }
}