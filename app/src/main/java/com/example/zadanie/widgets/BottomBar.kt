package com.example.zadanie.widgets

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.zadanie.R

class BottomBar : ConstraintLayout {
    private var active = -1

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun setActive(index: Int) {
        active = index
        updateItemAppearance()
    }

    private fun updateItemAppearance() {
        val mapImage = findViewById<ImageView>(R.id.map)
        val feedImage = findViewById<ImageView>(R.id.feed)
        val profileImage = findViewById<ImageView>(R.id.profile)

        // Reset all to inactive state
        resetToInactive(mapImage, R.drawable.baseline_map)
        resetToInactive(feedImage, R.drawable.baseline_feed)
        resetToInactive(profileImage, R.drawable.baseline_profile)

        // Set active state
        when (active) {
            MAP -> setActiveState(mapImage, R.drawable.baseline_map_white)
            FEED -> setActiveState(feedImage, R.drawable.baseline_feed_white)
            PROFILE -> setActiveState(profileImage, R.drawable.baseline_profile_white)
        }
    }

    private fun resetToInactive(imageView: ImageView, drawable: Int) {
        imageView.setImageResource(drawable)
        imageView.background = ContextCompat.getDrawable(context, R.drawable.rounded_bg_transparent)
    }

    private fun setActiveState(imageView: ImageView, drawable: Int) {
        imageView.setImageResource(drawable)
        imageView.background = ContextCompat.getDrawable(context, R.drawable.rounded_bg) // replace with your active background
    }

    fun init() {
        val layout =
            LayoutInflater.from(context)
                .inflate(R.layout.widget_bottom_bar, this, false)
        addView(layout)
        val mapImage = findViewById<ImageView>(R.id.map)
        val feedImage = findViewById<ImageView>(R.id.feed)
        val profileImage = findViewById<ImageView>(R.id.profile)

        // Reset all to inactive state
        resetToInactive(mapImage, R.drawable.baseline_map)
        resetToInactive(feedImage, R.drawable.baseline_feed)
        resetToInactive(profileImage, R.drawable.baseline_profile)

        layout.findViewById<ImageView>(R.id.map).setOnClickListener {
            if (active != MAP) {
                it.findNavController().navigate(R.id.action_to_map)
            }
        }
        layout.findViewById<ImageView>(R.id.feed).setOnClickListener {
            if (active != FEED) {
                it.findNavController().navigate(R.id.action_to_feed)
            }
        }
        layout.findViewById<ImageView>(R.id.profile).setOnClickListener {
            if (active != PROFILE) {
                it.findNavController().navigate(R.id.action_to_profile)
            }
        }
    }


    companion object {
        const val MAP = 0
        const val FEED = 1
        const val PROFILE = 2
    }
}