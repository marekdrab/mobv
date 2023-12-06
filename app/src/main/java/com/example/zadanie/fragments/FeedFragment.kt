package com.example.zadanie.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zadanie.adapters.FeedAdapter
import com.example.zadanie.R
import com.example.zadanie.api.DataRepository
import com.example.zadanie.data.PreferenceData
import com.example.zadanie.database.entities.UserEntity
import com.example.zadanie.viewModels.FeedViewModel
import com.example.zadanie.widgets.BottomBar
import com.example.zadanie.databinding.FragmentFeedBinding
import com.google.android.material.snackbar.Snackbar

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private lateinit var viewModel: FeedViewModel
    private lateinit var binding: FragmentFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FeedViewModel(DataRepository.getInstance(requireContext())) as T
            }
        })[FeedViewModel::class.java]    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFeedBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        binding.apply {
            bottomBar.setActive(BottomBar.FEED)
        }

        val isLocationSharingEnabled = PreferenceData.getInstance().getSharing(requireContext()).toString() == "true"

        if (isLocationSharingEnabled) {
            binding.feedRecyclerview.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
            setupRecyclerView()
        } else {
            binding.feedRecyclerview.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            bottomBar.setActive(BottomBar.FEED)

            feedRecyclerview.layoutManager = LinearLayoutManager(context)
            val feedAdapter = FeedAdapter()
            feedRecyclerview.adapter = feedAdapter

            viewModel.feed_items.observe(viewLifecycleOwner) { items ->
                feedAdapter.updateItems(items ?: emptyList())
            }

            pullRefresh.setOnRefreshListener {
                viewModel.updateItems()
            }
            viewModel.loading.observe(viewLifecycleOwner) {
                pullRefresh.isRefreshing = it
            }
        }
    }

}