package com.dicoding.alfigithubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.alfigithubuser.data.database.Favorite
import com.dicoding.alfigithubuser.databinding.ItemUserBinding

class FavoriteAdapter : ListAdapter<Favorite, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    //open function for searchview event listener
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Favorite) {

            //searchview listener
            binding.root.setOnClickListener { onItemClickCallback.onItemClicked(users) }

            //show to recyclerview
            binding.tvUser.text = "${users.username}"
            Glide.with(binding.root)
                .load(users.avatar) // URL Gambar
                .into(binding.ivUser)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite)
    }

}