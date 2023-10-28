package com.riandinp.freegamesdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riandinp.freegamesdb.R
import com.riandinp.freegamesdb.databinding.ItemScreenshotBinding
import com.riandinp.freegamesdb.utils.loadImage

class CardScreenshotsAdapter :
    ListAdapter<String, CardScreenshotsAdapter.CardScreenshotsViewHolder>(DIFF_CALLBACK) {

    inner class CardScreenshotsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemScreenshotBinding.bind(itemView)
        fun bindUrl(imageUrl: String) {
            with(binding) {
                ivScreenshot.loadImage(imageUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardScreenshotsViewHolder =
        CardScreenshotsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_screenshot, parent, false)
        )

    override fun onBindViewHolder(holder: CardScreenshotsViewHolder, position: Int) {
        holder.bindUrl(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}