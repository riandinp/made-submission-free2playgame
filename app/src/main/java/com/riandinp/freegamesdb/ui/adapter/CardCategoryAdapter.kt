package com.riandinp.freegamesdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.riandinp.freegamesdb.R
import com.riandinp.freegamesdb.databinding.ItemCategoryBinding

class CardCategoryAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<CardCategoryAdapter.CardCategoryViewHolder>() {

    private val listCategory = listOf(
        "RPG",
        "Social",
        "Shooter",
        "Strategy",
        "Fighting",
        "Card Game",
        "MOBA",
        "Battle Royale",
        "Sports",
        "Racing",
        "Fantasy"
    )

    interface OnItemClickListener {
        fun onItemClicked(data: String)
    }

    inner class CardCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCategoryBinding.bind(itemView)
        fun bindData(data: String) {
            with(binding) {
                btnCategory.text = data
                btnCategory.setOnClickListener {
                    onItemClickListener.onItemClicked(data)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardCategoryViewHolder =
        CardCategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )

    override fun getItemCount(): Int = listCategory.size

    override fun onBindViewHolder(holder: CardCategoryViewHolder, position: Int) {
        holder.bindData(listCategory[position])
    }
}