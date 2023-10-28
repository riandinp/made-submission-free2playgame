package com.riandinp.freegamesdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riandinp.freegamesdb.R
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.databinding.ItemGameBinding
import com.riandinp.freegamesdb.utils.getPublisherDeveloper
import com.riandinp.freegamesdb.utils.loadImage

class CardGameAdapter(private val onItemClickListener: OnItemClickListener? = null) : ListAdapter<Game, CardGameAdapter.CardGameViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardGameViewHolder =
        CardGameViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )

    override fun onBindViewHolder(holder: CardGameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onItemClickListener(game: Game)
    }

    inner class CardGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGameBinding.bind(itemView)
        fun bind(data: Game) {
            with(binding) {
                ivThumbnail.loadImage(data.thumbnail)
                tvTitleGame.text = data.title
                tvPublisherDeveloper.text = getPublisherDeveloper(data.publisher, data.developer)
                tvReleaseDate.text = data.releaseDate
                clContent.setOnClickListener { onItemClickListener?.onItemClickListener(data) }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean =
                Game(
                    id = oldItem.id,
                    title= oldItem.title,
                    thumbnail = oldItem.thumbnail,
                    shortDescription = oldItem.shortDescription,
                    gameUrl = oldItem.gameUrl,
                    platform = oldItem.platform,
                    publisher = oldItem.publisher,
                    developer = oldItem.developer,
                    releaseDate = oldItem.releaseDate,
                    genre = oldItem.genre,
                    freetogameProfileUrl = oldItem.freetogameProfileUrl
                ) == Game(
                    id = newItem.id,
                    title= newItem.title,
                    thumbnail = newItem.thumbnail,
                    shortDescription = newItem.shortDescription,
                    gameUrl = newItem.gameUrl,
                    platform = newItem.platform,
                    publisher = newItem.publisher,
                    developer = newItem.developer,
                    releaseDate = newItem.releaseDate,
                    genre = newItem.genre,
                    freetogameProfileUrl = newItem.freetogameProfileUrl
                )
        }
    }
}