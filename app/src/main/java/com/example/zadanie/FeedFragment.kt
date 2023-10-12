package com.example.zadanie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment

class FeedFragment : Fragment(R.layout.fragment_feed) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("test", "test")
    }
}