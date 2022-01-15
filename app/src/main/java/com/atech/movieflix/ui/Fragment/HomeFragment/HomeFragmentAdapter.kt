package com.atech.movieflix.ui.Fragment.HomeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atech.movieflix.R
import com.atech.movieflix.data.DiffUtilsMovie
import com.atech.movieflix.data.Movie
import com.atech.movieflix.databinding.RowMovieBinding
import com.atech.movieflix.utils.loadImage

class HomeFragmentAdapter(
    private val listener: (view: View, movie: Movie) -> Unit
) :
    ListAdapter<Movie, HomeFragmentAdapter.HomeFragmentViewHolder>(DiffUtilsMovie()) {

    inner class HomeFragmentViewHolder(
        private val binding: RowMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val movie = getItem(position)
                    listener.invoke(binding.root, movie)
                }
            }
        }

        fun bind(movie: Movie) {
            binding.apply {
                binding.root.transitionName = movie.show.name
                textViewTitle.text = movie.show.name
                textViewRating.text = (movie.show.rating.average ?: 0.0).toString()
                if (movie.show.image == null) {
                    binding.imageViewThumbnail.setImageResource(R.drawable.ic_no_image)
                    binding.progressBar.isVisible = false
                }
                movie.show.image?.original?.loadImage(
                    binding.root,
                    binding.imageViewThumbnail,
                    binding.progressBar,
                    25
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder =
        HomeFragmentViewHolder(
            RowMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}