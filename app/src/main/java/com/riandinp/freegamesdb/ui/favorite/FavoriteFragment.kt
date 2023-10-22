package com.riandinp.freegamesdb.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.ui.CardGameAdapter
import com.riandinp.freegamesdb.core.ui.ViewModelFactory
import com.riandinp.freegamesdb.databinding.FragmentFavoriteBinding
import com.riandinp.freegamesdb.ui.detail.DetailGameActivity

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var gameAdapter: CardGameAdapter

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        setRecycleView()
        setObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObserver() {
        favoriteViewModel.favoriteGames.observe(viewLifecycleOwner) { games ->
            gameAdapter.submitList(games)
            binding.vwNoData.root.isVisible = games.isNullOrEmpty()
        }
    }

    private fun setRecycleView() {
        gameAdapter = CardGameAdapter(object : CardGameAdapter.OnItemClickListener {
            override fun onItemClickListener(game: Game) {
                DetailGameActivity.start(requireActivity(), game)
            }
        })

        binding.rvCardGame.apply {
            adapter = gameAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}