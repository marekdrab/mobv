package com.example.zadanie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private lateinit var viewModel: FeedViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.forgotten_pw).apply {
            setOnClickListener {
                it.findNavController().navigate(R.id.action_change_pw)
            }
        }
        viewModel = ViewModelProvider(requireActivity())[FeedViewModel::class.java]

        viewModel.feed_items.observe(viewLifecycleOwner) { items ->
            // Tu môžete aktualizovať UI podľa hodnoty stringValue
            Log.d("Profile", "prvky su $items")
        }
    }
}