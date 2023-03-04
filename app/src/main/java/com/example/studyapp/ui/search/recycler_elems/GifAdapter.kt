package com.example.studyapp.ui.search.recycler_elems

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.ui.search.GifSelectionListener
import com.example.studyapp.R
import com.example.studyapp.ui.models.GifModelUI

class GifAdapter : RecyclerView.Adapter<GifViewHolder>() {
    private val gifList: MutableList<GifModelUI> = mutableListOf()
    private lateinit var gifSelectionListener: GifSelectionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gif_item, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        Log.d("Recycler", "Current position is $position")
        val gif = gifList[position]
        holder.bind(gif) {
            gifSelectionListener.selected(position)
        }
    }

    private fun addGifs(gifs: List<GifModelUI>) {
        val gifPos = gifList.size
        gifList.clear()
        gifList.addAll(gifs)
        notifyItemRangeInserted(gifPos, gifs.size - gifPos)
    }

    fun replaceGifs(gifs: List<GifModelUI>) {
        if (gifs.containsAll(gifList)) {
            addGifs(gifs)
        } else {
            val oldCount = gifList.size
            gifList.clear()
            gifList.addAll(gifs)
            notifyItemRangeRemoved(0, oldCount)
            notifyItemRangeInserted(0, gifs.size)
        }
    }

    override fun getItemCount(): Int {
        return gifList.size
    }

    fun setGifSelectionListener(listener: GifSelectionListener) {
        gifSelectionListener = listener
    }
}