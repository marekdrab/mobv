package com.example.zadanie.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie.R
import com.example.zadanie.config.Config
import com.example.zadanie.database.entities.UserEntity
import com.example.zadanie.fragments.FeedFragment
import com.example.zadanie.fragments.FeedFragmentDirections
import com.example.zadanie.utils.ItemDiffCallback
import com.squareup.picasso.Picasso

class FeedAdapter() : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    private var items: List<UserEntity> = listOf()

    // ViewHolder poskytuje odkazy na zobrazenia v každej položke
    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Táto metóda vytvára nový ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feed_item, parent, false)
        return FeedViewHolder(view)
    }

    // Táto metóda prepojí dáta s ViewHolderom
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.findViewById<TextView>(R.id.item_text).text = item.name

        val imageView = holder.itemView.findViewById<ImageView>(R.id.item_image)
        if (item.photo.isNotEmpty()) {
            Picasso.get()
                .load(Config.IMAGE_BASE_URL + item.photo) // Complete URL
                .into(imageView) // ImageView to load the image into
        } else {
            // Optional: Handle the case where there is no image URL
            imageView.setImageResource(R.drawable.baseline_profile) // Replace with your default image
        }

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedToDetail()
            action.id = items[position].uid
            it.findNavController().navigate(action)
        }
    }

    // Vracia počet položiek v zozname
    override fun getItemCount() = items.size

    fun updateItems(newItems: List<UserEntity>) {
        val diffCallback = ItemDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

}