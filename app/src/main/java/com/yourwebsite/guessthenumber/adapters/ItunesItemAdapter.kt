package com.yourwebsite.guessthenumber.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yourwebsite.guessthenumber.ItunesItem
import com.yourwebsite.guessthenumber.databinding.LayoutItemAlbumBinding
import kotlin.collections.ArrayList

class ItunesItemAdapter(var itunesItems: ArrayList<ItunesItem>, val context: Context) : RecyclerView.Adapter<ItunesItemAdapter.ItunesItemHolder>() {
    class ItunesItemHolder(val binding: LayoutItemAlbumBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItunesItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemAlbumBinding.inflate(layoutInflater)

        return ItunesItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItunesItemHolder, position: Int) {
        val item = itunesItems[position]
        holder.binding.item = item

        Glide.with(context)
            .asBitmap()
            .load(item.artworkUrl100)
            .centerCrop()
            .into(holder.binding.imgAlbum)

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return itunesItems.size
    }

}