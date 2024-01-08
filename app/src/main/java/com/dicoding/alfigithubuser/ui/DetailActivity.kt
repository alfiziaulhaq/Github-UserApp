package com.dicoding.alfigithubuser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.alfigithubuser.R
import com.dicoding.alfigithubuser.data.database.Favorite
import com.dicoding.alfigithubuser.data.repository.FavoriteModelFactory
import com.dicoding.alfigithubuser.data.response.DetailResponse
import com.dicoding.alfigithubuser.databinding.ActivityDetailBinding
import com.dicoding.alfigithubuser.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        FavoriteModelFactory.getInstance(application)
    }

    private var favorite: Favorite? = null
    private var fetch : String? = ""

    companion object{
        private const val TAG = "DetailViewModel"
        const val EXTRA_USERNAME="extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //create binding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetch = intent.getStringExtra(EXTRA_USERNAME)
        detailViewModel.findDetail(fetch.toString())

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val sectionsPagerAdapter = PagerAdapter(this)
        sectionsPagerAdapter.username= fetch.toString()

        val viewPager: ViewPager2 = findViewById(R.id.pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.datadetail.observe(this){user->
            setDataDetail(user)
            setFab(user)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun setFab(data: DetailResponse){

        detailViewModel.getAll.observe(this@DetailActivity) { favoriteUsers ->
            favorite = Favorite(data.login, data.avatarUrl)
            val isFavorite = favoriteUsers.any { it.username == fetch }
            val favoriteImageResource =
                if (isFavorite) R.drawable.ic_favorite_full else R.drawable.ic_favorite

            binding.fab.setImageResource(favoriteImageResource)
            binding.fab.setOnClickListener {
                try {
                    if (isFavorite) {
                        detailViewModel.delete(favorite as Favorite)
                        fun snack(view: View){
                            Snackbar.make(view, "This user has been removed ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()}
                        snack(it)
                    } else {
                        detailViewModel.insert(favorite as Favorite)
                        fun snack(view: View){
                            Snackbar.make(view, "This user has been added ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()}
                        snack(it)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun setDataDetail(data: DetailResponse) {
        binding.tvUser.text = data.login
        val test = data.name
        if(test==null){
            binding.tvName.text = "-"
        }else binding.tvName.text = data.name.toString()

        binding.tvFollower.text = data.followers.toString()+" followers"
        binding.tvFollowing.text = data.following.toString()+" following"

        Glide.with(this@DetailActivity)
            .load(data.avatarUrl)
            .into(binding.ivPicture)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}