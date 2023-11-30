package com.example.zadanie.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.zadanie.R
import com.example.zadanie.config.Config
import com.squareup.picasso.Picasso

object BindingAdapters {
    @JvmStatic @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get().load(Config.IMAGE_BASE_URL + imageUrl).into(view)
        } else {
            // Optionally handle the case where imageUrl is null or empty
            view.setImageResource(R.drawable.baseline_profile)
        }
    }
}