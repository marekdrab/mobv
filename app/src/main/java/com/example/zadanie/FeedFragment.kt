package com.example.zadanie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private lateinit var viewModel: FeedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<BottomBar>(R.id.bottom_bar).setActive(BottomBar.FEED)

        // Inicializácia ViewModel
        viewModel = ViewModelProvider(requireActivity())[FeedViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.feed_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val feedAdapter = FeedAdapter()
        recyclerView.adapter = feedAdapter

        // Pozorovanie zmeny hodnoty
        viewModel.feed_items.observe(viewLifecycleOwner) { items ->
            Log.d("FeedFragment", "nove hodnoty $items")
            feedAdapter.updateItems(items)
        }

        view.findViewById<Button>(R.id.btn_generate).setOnClickListener {
            viewModel.updateItems()
        }

    }
}