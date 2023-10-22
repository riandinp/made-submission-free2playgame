package com.riandinp.freegamesdb.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.like.LikeButton
import com.like.OnLikeListener
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

        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, binding.root).isAppearanceLightStatusBars = true

        val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        setSupportActionBar(binding.toolbar)

        val detailGame = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, Game::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        if (detailGame != null) {
            binding.tvToolbarTitle.text = detailGame.title
            binding.apply {
                Glide.with(this@DetailGameActivity)
                    .load(detailGame.thumbnail)
                    .placeholder(R.drawable.placeholder)
                    .into(ivThumbnail)

                tvTitleGame.text = detailGame.title
                // show Platform icon
                ivWindows.isVisible = detailGame.platform.contains("PC", true)
                ivBrowser.isVisible = detailGame.platform.contains("Web", true)
            }
            initObserver(detailGame)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout?.totalScrollRange!!
                } else {
                    Log.d("OffsetAppBar", "verticalOffset = $verticalOffset, scrollRange = $scrollRange")
                    binding.tvToolbarTitle.isVisible = scrollRange + verticalOffset <= 80
                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setLoadingScreen(value: Boolean) {
        binding.apply {
            content.root.isVisible = !value
            vwError.root.isVisible = !value
            favoriteButton.isVisible = !value
            pbLoadingScreen.isVisible = value
        }
    }

    private fun initObserver(detailGame: Game) {
        detailViewModel.getDetailGame(detailGame).observe(this) { detailGames ->
            when (detailGames) {
                is Resource.Loading -> {
                    setLoadingScreen(true)
                }

                is Resource.Success -> {
                    setLoadingScreen(false)
                    showDetailData(detailGames.data)
                }

                is Resource.Error -> {
                    setLoadingScreen(false)
                    binding.content.root.isVisible = false
                    binding.vwError.root.isVisible = true
                    Toast.makeText(this@DetailGameActivity, detailGames.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showDetailData(game: Game?) {
        binding.apply {
            if (game != null) {
                content.root.isVisible = true
                vwError.root.isVisible = false
                // set isFavorite
                favoriteButton.apply {
                    isLiked = game.isFavorite
                    setOnLikeListener(object : OnLikeListener {
                        override fun liked(likeButton: LikeButton) {
                            detailViewModel.setFavoriteGame(game, true)
                        }

                        override fun unLiked(likeButton: LikeButton) {
                            detailViewModel.setFavoriteGame(game, false)
                        }
                    })
                }

                // set content
                content.apply {
                    tvDescriptionDetail.text = game.description ?: ""
                    tvPublisherDeveloperDetail.text =
                        getPublisherDeveloper(game.publisher, game.developer)
                    tvReleaseDateDetail.text = game.releaseDate
                }

            } else {
                content.root.isVisible = false
                vwError.root.isVisible = true
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