package com.yourwebsite.guessthenumber.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yourwebsite.guessthenumber.HighScoreItem
import com.yourwebsite.guessthenumber.ItunesItem
import com.yourwebsite.guessthenumber.databinding.LayoutItemAlbumBinding
import com.yourwebsite.guessthenumber.databinding.LayoutItemHighScoreBinding
import kotlin.collections.ArrayList

class HighScoreItemAdapter(var highScoreItems: ArrayList<HighScoreItem>, val context: Context) : RecyclerView.Adapter<HighScoreItemAdapter.HighScoreItemHolder>() {
    class HighScoreItemHolder(val binding: LayoutItemHighScoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemHighScoreBinding.inflate(layoutInflater)

        return HighScoreItemHolder(binding)
    }

    override fun onBindViewHolder(holder: HighScoreItemHolder, position: Int) {
        val item = highScoreItems[position]
        holder.binding.item = item

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return highScoreItems.size
    }

}