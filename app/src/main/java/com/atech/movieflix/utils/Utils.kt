package com.atech.movieflix.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.atech.movieflix.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * Load image in ImageView
 * @param parentView Parent View or Context
 * @param view ImageView
 * @param progressBar ProgressBar (Nullable)
 * @author Ayaan
 * @since 4.0.2
 */
fun String.loadImage(
    parentView: View,
    view: ImageView,
    progressBar: ProgressBar?,
    cornerRadius: Int,

    ) =
    Glide.with(parentView)
        .load(this)
        .error(R.drawable.ic_no_image)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }

        })
        .apply(RequestOptions.bitmapTransform(RoundedCorners(cornerRadius)))
        .timeout(6000)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)