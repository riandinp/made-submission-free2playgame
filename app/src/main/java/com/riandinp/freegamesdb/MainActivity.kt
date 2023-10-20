package com.riandinp.freegamesdb

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.ui.CardGameAdapter
import com.riandinp.freegamesdb.core.ui.ViewModelFactory
import com.riandinp.freegamesdb.databinding.ActivityMainBinding
import com.riandinp.freegamesdb.ui.detail.DetailGameActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var gameAdapter: CardGameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setRecycleView()
        setObserver()
    }

    private fun setObserver() {
        mainViewModel.games.observe(this) { games ->
            if (games != null) {
                when (games) {
                    is Resource.Loading -> binding.pbLoadingScreen.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.pbLoadingScreen.visibility = View.GONE
                        gameAdapter.submitList(games.data)
                    }
                    is Resource.Error -> {
                        binding.pbLoadingScreen.visibility = View.GONE
                        binding.vwError.root.visibility = View.VISIBLE
                        binding.vwError.tvError.text = games.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
    }

    private fun setRecycleView() {
        gameAdapter = CardGameAdapter(object : CardGameAdapter.OnItemClickListener {
            override fun onItemClickListener(game: Game) {
                DetailGameActivity.start(this@MainActivity, game)
            }
        })

        binding.rvCardGame.apply {
            adapter = gameAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }
}