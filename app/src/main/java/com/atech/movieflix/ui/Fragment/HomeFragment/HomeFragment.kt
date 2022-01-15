package com.atech.movieflix.ui.Fragment.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.atech.movieflix.R
import com.atech.movieflix.databinding.FragmentHomeBinding
import com.atech.movieflix.utils.DataState
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()
    private val TAG = "HomeFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        val homeAdapter = HomeFragmentAdapter() { v, movie ->
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
            try {
                val extras = FragmentNavigatorExtras(v to movie.show.name)
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    movie,
                    movie.show.name
                )
                findNavController().navigate(action, extras)
            } catch (e: Exception) {
                Log.e(TAG, "$e")
            }
        }
        binding.apply {
            recyclerViewShowMovies.apply {
                adapter = homeAdapter
                layoutManager = StaggeredGridLayoutManager(3, LinearLayout.VERTICAL)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getData()
            viewModel.dataState.observe(viewLifecycleOwner) {
                when (it) {
                    is DataState.Error -> {
                        Toast.makeText(requireContext(), "${it.exception}", Toast.LENGTH_SHORT)
                            .show()
                        Log.e(TAG, "onViewCreated: ${it.exception}")
                    }
                    DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        Log.d(TAG, "onViewCreated: ${it.data[0].show.externals.imdb}")
                        homeAdapter.submitList(it.data)
                    }
                }
            }
        }
    }
}