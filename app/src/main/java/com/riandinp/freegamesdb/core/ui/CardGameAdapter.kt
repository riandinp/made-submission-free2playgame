package com.riandinp.freegamesdb.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riandinp.freegamesdb.R
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.databinding.ItemGameBinding

class CardGameAdapter : ListAdapter<Game, CardGameAdapter.CardGameViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardGameViewHolder =
        CardGameViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )

    override fun onBindViewHolder(holder: CardGameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getPublisherDeveloper(publisher: String, developer: String): String {
        return if (publisher == developer) publisher
        else "${publisher}/${developer}"
    }

    inner class CardGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGameBinding.bind(itemView)
        fun bind(data: Game) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.thumbnail)
                    .placeholder(R.drawable.placeholder)
                    .into(ivThumbnail)
                tvTitleGame.text = data.title
                tvPublisherDeveloper.text = getPublisherDeveloper(data.publisher, data.developer)
                tvReleaseDate.text = data.releaseDate
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem == newItem
        }
    }
}