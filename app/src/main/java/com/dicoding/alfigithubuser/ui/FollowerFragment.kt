package com.dicoding.alfigithubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.alfigithubuser.R
import com.dicoding.alfigithubuser.data.response.ItemsItem
import com.dicoding.alfigithubuser.databinding.FragmentFollowBinding
import com.dicoding.alfigithubuser.viewmodel.FragmentViewModel


class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: FollowAdapter
    private lateinit var viewModel: FragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowAdapter()
        binding.rvItem.adapter = adapter
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvItem.layoutManager = layoutManager

        viewModel = ViewModelProvider(this)[FragmentViewModel::class.java]

        val username = arguments?.getString(ARG_USERNAME)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        if (index == 1) {
            viewModel.findFollower(username.toString())
            viewModel.listfollower.observe(viewLifecycleOwner) {
                setUserData(it)
            }
        } else {
            viewModel.findFollowing(username.toString())
            viewModel.listfollowing.observe(viewLifecycleOwner) {
                setUserData(it)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setUserData(data: List<ItemsItem>) {
        adapter = FollowAdapter()
        adapter.submitList(data)
        binding.rvItem.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }
}
