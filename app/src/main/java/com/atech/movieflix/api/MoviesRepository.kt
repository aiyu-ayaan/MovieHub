package com.atech.movieflix.api

import com.atech.movieflix.data.Movie
import com.atech.movieflix.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val movieApi: MovieApi
) {

    suspend fun getMovies(query: String): Flow<DataState<List<Movie>>> =
        flow {
            emit(DataState.Loading)
            try {
                val list = movieApi.getMovies(query)
                emit(DataState.Success(list))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
}