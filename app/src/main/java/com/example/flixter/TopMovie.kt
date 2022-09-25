package com.example.flixter

import com.google.gson.annotations.SerializedName

/**
 * The Model for storing a single movie
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */
class TopMovie(movie_title:String,movie_description:String,movie_imageURL:String) {

    val title = movie_title
    val description = movie_description
    val imageURL = "https://image.tmdb.org/t/p/w500/" + movie_imageURL
//    @JvmField
//    @SerializedName("title")
//    var title: String? = null
//
//
//    @SerializedName("movie_image")
//    var movieImageUrl: String? = null
//
//
//    @SerializedName("poster_path")
//    var description: String? = null

    //TODO-STRETCH-GOALS
}