package com.atech.movieflix.ui.Fragment.SearchFragment

import androidx.lifecycle.ViewModel
import com.atech.movieflix.api.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    val query = MutableStateFlow("all")
    val dataState = query.flatMapLatest {
        repository.getMovies(it)
    }
}