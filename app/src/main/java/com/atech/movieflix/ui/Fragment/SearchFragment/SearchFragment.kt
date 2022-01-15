package com.atech.movieflix.ui.Fragment.SearchFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.viewbinding.library.fragment.viewBinding
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.atech.movieflix.R
import com.atech.movieflix.databinding.FragmentSearchBinding
import com.atech.movieflix.ui.Fragment.HomeFragment.HomeFragmentAdapter
import com.atech.movieflix.utils.DataState
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding()
    private val TAG = "SearchFragment"
    private val viewModel: SearchFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val homeAdapter = HomeFragmentAdapter() { v, movie ->
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
            try {
                val extras = FragmentNavigatorExtras(v to movie.show.name)
                val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                    movie,
                    movie.show.name
                )
                findNavController().navigate(action, extras)
            } catch (e: Exception) {
                Log.e(TAG, "$e")
            }
        }

        binding.apply {
            activity?.findViewById<EditText>(R.id.searchInput).apply {
                this?.let {
                    requestFocus()
                    showKeyboard()
                    viewModel.query.value =
                        if (activity?.findViewById<EditText>(R.id.searchInput)?.text.toString()
                                .isBlank()
                        ) "all"
                        else
                            (activity?.findViewById<EditText>(R.id.searchInput)?.text.toString())
                    addTextChangedListener {
                        if (it != null) {
                            viewModel.query.value = if (it.isBlank()) "all"
                            else it.toString()
                        }
                    }
                }
            }
            showSearchResult.apply {
                adapter = homeAdapter
                layoutManager = StaggeredGridLayoutManager(3, LinearLayout.VERTICAL)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.dataState.collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "onViewCreated: ${dataState.exception}")
                    }
                    DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        binding.noResult.isVisible = dataState.data.isEmpty()
                        homeAdapter.submitList(dataState.data)
                    }
                }
            }
        }
    }

    private fun showKeyboard() = binding.apply {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .apply {
                this.showSoftInput(activity?.findViewById<EditText>(R.id.searchInput), 0)
            }
    }

    private fun hideKeyboard() = binding.apply {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .apply {
                this.hideSoftInputFromWindow(
                    activity?.findViewById<EditText>(R.id.searchInput)?.windowToken,
                    0
                )
            }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }
}