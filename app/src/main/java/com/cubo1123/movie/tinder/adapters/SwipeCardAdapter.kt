package com.cubo1123.movie.tinder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cubo1123.movie.tinder.databinding.ItemProfileBinding
import com.cubo1123.movie.tinder.domain.MovieProfile

class SwipeCardAdapter(private val clickListener: PickerProfileListener) : ListAdapter<MovieProfile,SwipeCardAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

    class ViewHolder(val binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : MovieProfile,clickListener: PickerProfileListener){
            binding.itemName.text = "${item.id}. ${item.title}"
            binding.itemCity.text = item.overview
            Glide.with(binding.itemImage)
                .load("https://image.tmdb.org/t/p/original"+item.posterUrl)
                .into(binding.itemImage)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProfileBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieProfile>() {

        override fun areItemsTheSame(oldItem: MovieProfile, newItem: MovieProfile): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieProfile, newItem: MovieProfile): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class PickerProfileListener(val clickListener: (id: Int) -> Unit) {
        fun onClick(profile: MovieProfile) = clickListener(profile.id)
    }
}