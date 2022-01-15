package com.atech.movieflix.ui.Fragment.DetailFragment

import android.os.Bundle
import android.text.Html
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.atech.movieflix.R
import com.atech.movieflix.databinding.FragmentDetailBinding
import com.atech.movieflix.utils.loadImage
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)

        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie
        binding.apply {
            textViewTitle.text = movie.show.name
            textViewLanguage.text = movie.show.language
            var generes = ""
            movie.show.genres.forEach {
                generes += "$it "
            }
            textViewPremiered.text = context?.getString(R.string.premiered, movie.show.premiered)
            textViewRating.text = (movie.show.rating.average ?: 0).toString()
            textViewGenres.text = generes
            if (movie.show.image == null) {
                binding.imageViewThumbnail.setImageResource(R.drawable.ic_no_image)
                binding.progressBar2.isVisible = false
            }
            movie.show.image?.original?.loadImage(
                binding.root,
                binding.imageViewThumbnail,
                binding.progressBar2,
                1
            )
            var day = ""
            movie.show.schedule.days.forEach {
                day += "$it "
            }
            val time = movie.show.schedule.time
            textViewSchedule.text = "$day at $time"
            val body = Html
                .fromHtml(
                    "<![CDATA[<body style=\"text-align:justify; background-color:rgb(31,31,31);color:rgb(255,255,255);\">"
                            + movie.show.summary
                            + "</body>]]>", HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
            webViewParagraph.loadData(
                body,
                "text/html; charset=utf-8",
                "utf-8"
            )
            binding.buttonPrevious.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    movie.show._links.previousepisode.href,
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.buttonSelf.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    movie.show._links.self.href,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }
}