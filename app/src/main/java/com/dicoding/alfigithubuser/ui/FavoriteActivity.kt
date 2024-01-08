package com.dicoding.alfigithubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.alfigithubuser.R
import com.dicoding.alfigithubuser.data.database.Favorite
import com.dicoding.alfigithubuser.data.repository.FavoriteModelFactory
import com.dicoding.alfigithubuser.data.response.ItemsItem
import com.dicoding.alfigithubuser.databinding.ActivityFavoriteBinding
import com.dicoding.alfigithubuser.databinding.ActivityMainBinding
import com.dicoding.alfigithubuser.viewmodel.DetailViewModel
import com.dicoding.alfigithubuser.viewmodel.FavoriteViewModel
import com.dicoding.alfigithubuser.viewmodel.MainViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteModelFactory.getInstance(application)
    }
    companion object {
        private const val TAG = "MainViewModel"
        private const val parameter_query = "alfizi"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //create binding
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //create recyclerview
        val layoutManager = LinearLayoutManager(this)
        binding.rvItem.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvItem.addItemDecoration(itemDecoration)

        //observer recyclerview user
        favoriteViewModel.getAll().observe(this) {
            setUserData(it)
        }

    }

    private fun setUserData(data: List<Favorite>) {
        val adapter = FavoriteAdapter()
        adapter.submitList(data)
        binding.rvItem.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Favorite) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
                    startActivity(it)
                }
            }
        }
        )
    }

}