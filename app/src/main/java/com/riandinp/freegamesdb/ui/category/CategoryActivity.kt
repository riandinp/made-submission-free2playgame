package com.riandinp.freegamesdb.ui.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.ui.CardGameAdapter
import com.riandinp.freegamesdb.core.ui.ViewModelFactory
import com.riandinp.freegamesdb.databinding.ActivityCategoryBinding
import com.riandinp.freegamesdb.ui.detail.DetailGameActivity

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var gameAdapter: CardGameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.title = null

        val factory = ViewModelFactory.getInstance(this)
        categoryViewModel = ViewModelProvider(this, factory)[CategoryViewModel::class.java]

        setRecycleView()

        val category = intent.getStringExtra(CATEGORY_KEY)
        if(category != null) {
            binding.tvToolbarTitle.text = category
            initObserver(category)
        }
    }

    private fun setRecycleView() {
        gameAdapter = CardGameAdapter(object : CardGameAdapter.OnItemClickListener {
            override fun onItemClickListener(game: Game) {
                DetailGameActivity.start(this@CategoryActivity, game)
            }
        })

        binding.rvCardGame.apply {
            adapter = gameAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun initObserver(category: String) {
        categoryViewModel.getListGames(category).observe(this) { games ->
            gameAdapter.submitList(games)
        }
    }

    companion object {
        private const val CATEGORY_KEY = "category_key"

        @JvmStatic
        fun start(context: Context, category: String) {
            val starter = Intent(context, CategoryActivity::class.java)
                .putExtra(CATEGORY_KEY, category)
            context.startActivity(starter)
        }
    }
}