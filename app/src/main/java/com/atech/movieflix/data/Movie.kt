package com.atech.movieflix.data

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Keep
data class Movie(
    val score: Double?,
    val show: Show
) : Parcelable

@Parcelize
@Keep
data class Show(
    val id: Int,
    val url: String,
    val name: String,
    val type: String,
    val language: String,
    val genres: ArrayList<String>,
    val status: String,
    val runtime: Int?,
    val averageRuntime: Int,
    val premiered: String,
    val ended: String?,
    val officialSite: String?,
    val schedule: Schedule,
    val rating: Rating,
    val weight: Double,
    val network: Network?,
    val webChannel: WebChannel?,
    val externals: Externals,
    val image: Image?,
    val summary: String,
    val updated: Int,
    val _links: Links
) : Parcelable

@Parcelize
@Keep
data class Schedule(
    val time: String,
    val days: ArrayList<String>
) : Parcelable

@Parcelize
@Keep
data class Rating(
    val average: Double?
) : Parcelable

@Parcelize
@Keep
data class Network(
    val id: Int?,
    val name: String?,
    val country: Country?
) : Parcelable

@Parcelize
@Keep
data class Country(
    val name: String,
    val code: String,
    val timezone: String
) : Parcelable

@Parcelize
@Keep
data class WebChannel(
    val id: Int?,
    val name: String?,
    val country: Country?
) : Parcelable

@Parcelize
@Keep
data class Externals(
   
    val thetvdb: Int?,
    val imdb: String?
) : Parcelable

@Parcelize
@Keep
data class Image(
    val medium: String,
    val original: String
) : Parcelable

@Parcelize
@Keep
data class Links(
    val self: Self,
    val previousepisode: previousepisode
) : Parcelable

@Parcelize
@Keep
data class Self(
    val href: String
) : Parcelable

@Parcelize
@Keep
data class previousepisode(
    val href: String
) : Parcelable

class DiffUtilsMovie : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        newItem == oldItem

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        newItem == oldItem

}

