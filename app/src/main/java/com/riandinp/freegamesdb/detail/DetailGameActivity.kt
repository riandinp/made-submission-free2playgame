package com.riandinp.freegamesdb.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.riandinp.freegamesdb.R
import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.ui.ViewModelFactory
import com.riandinp.freegamesdb.databinding.ActivityDetailGameBinding
import com.riandinp.freegamesdb.utils.getPublisherDeveloper

class DetailGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailGameBinding
    private lateinit var detailViewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailGame = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, Game::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        if (detailGame != null) {
            initObserver(detailGame)
        }

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout?.totalScrollRange!!
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.cToolbar.title = detailGame?.title
                    isShow = false
                } else if (!isShow) {
                    binding.cToolbar.title = " "
                    isShow = true
                }
            }
        })

    }

    private fun setLoadingScreen(value: Boolean) {
        binding.apply {
            nsvContent.isVisible = !value
            appBar.isVisible = !value
            pbLoadingScreen.isVisible = value
        }
    }

    private fun initObserver(detailGame: Game) {
        detailViewModel.getDetailGame(detailGame.id, detailGame).observe(this) { detailGames ->
            when(detailGames) {
                is Resource.Loading -> {
                    setLoadingScreen(true)
                }
                is Resource.Success -> {
                    setLoadingScreen(false)
                    showData(detailGames.data)
                }
                is Resource.Error -> {
                    setLoadingScreen(false)
                    Toast.makeText(this@DetailGameActivity, detailGames.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showData(game: Game?) {
        binding.apply {
            Glide.with(this@DetailGameActivity)
                .load(game?.thumbnail)
                .placeholder(R.drawable.placeholder)
                .into(ivThumbnail)
            tvTitleGame.text = game?.title
            content.apply {
                tvDescriptionDetail.text = game?.description ?:""
                tvPublisherDeveloperDetail.text = getPublisherDeveloper(game?.publisher ?:"Unknown", game?.developer ?:"Unknown")
                tvReleaseDateDetail.text = game?.releaseDate ?:""
            }
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        @JvmStatic
        fun start(context: Context, gameData: Game) {
            val starter = Intent(context, DetailGameActivity::class.java)
                .putExtra(EXTRA_DATA, gameData)
            context.startActivity(starter)
        }
    }
}