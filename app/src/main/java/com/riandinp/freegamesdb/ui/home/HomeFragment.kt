package com.riandinp.freegamesdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riandinp.freegamesdb.R
import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.ui.CardGameAdapter
import com.riandinp.freegamesdb.core.ui.ViewModelFactory
import com.riandinp.freegamesdb.databinding.FragmentHomeBinding
import com.riandinp.freegamesdb.ui.detail.DetailGameActivity

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var gameAdapter: CardGameAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        setRecycleView()
        setObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObserver() {
        homeViewModel.games.observe(viewLifecycleOwner) { games ->
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
        fun newInstance() =
            HomeFragment()
    }
}