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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setObserver()
    }

    private fun setObserver() {
        mainViewModel.games.observe(this) { games ->
            if (games != null) {
                when (games) {
                    is Resource.Loading -> binding.pbLoadingScreen.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.pbLoadingScreen.visibility = View.GONE
                        setRecycleView(games.data)
                    }

                    is Resource.Error -> {
                        binding.pbLoadingScreen.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setRecycleView(data: List<Game>?) {
        binding.rvCardGame.apply {
            val gameAdapter = CardGameAdapter()
            gameAdapter.submitList(data)
            adapter = gameAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}