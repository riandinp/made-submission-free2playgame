package com.riandinp.freegamesdb.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.riandinp.freegamesdb.databinding.FragmentCategoryListBinding
import com.riandinp.freegamesdb.ui.adapter.CardCategoryAdapter


class CategoryListFragment : Fragment() {

    private lateinit var categoryAdapter: CardCategoryAdapter

    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycleView()
    }

    private fun setRecycleView() {
        categoryAdapter = CardCategoryAdapter(object : CardCategoryAdapter.OnItemClickListener {
            override fun onItemClicked(data: String) {
                CategoryActivity.start(requireActivity(), data)
            }
        })

        binding.rvCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}