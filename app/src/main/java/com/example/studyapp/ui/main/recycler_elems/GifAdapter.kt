package com.example.studyapp.ui.main.recycler_elems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.R
import com.example.studyapp.ui.main.models.GifModelUI

class GifAdapter: RecyclerView.Adapter<GifViewHolder>() {
    private val gifList: MutableList<GifModelUI> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gif_item, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = gifList[position]
        holder.bind(gif)
    }

    fun addGif(gif: GifModelUI) {
        gifList.add(gif)
        notifyItemRangeInserted(gifList.size - 1, 1)
    }

    fun addGifs(gifs: List<GifModelUI>) {
        gifList.addAll(gifs)
        notifyItemRangeInserted(gifList.size - gifs.size, gifs.size)
    }

    fun replaceGifs(gifs: List<GifModelUI>) {
        val oldCount = gifList.size
        gifList.addAll(gifs)
        gifList.clear()
        notifyItemRangeRemoved(0, oldCount)
        notifyItemRangeInserted(0, gifs.size)
    }

    override fun getItemCount(): Int {
        return gifList.size
    }
}