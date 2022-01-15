package com.atech.movieflix.ui.Fragment.HomeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.movieflix.api.MoviesRepository
import com.atech.movieflix.data.Movie
import com.atech.movieflix.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<List<Movie>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Movie>>>
        get() = _dataState

    fun getData() {
        viewModelScope.launch {
            repository.getMovies("all").onEach { dataState ->
                _dataState.value = dataState
            }
                .launchIn(viewModelScope)
        }
    }
}