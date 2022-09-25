package com.example.flixter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class TopMovieFragment : Fragment(), OnListFragmentInteractionListener {

    /*
    * Constructing the view
    */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.top_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val param = RequestParams()
        param["api-key"] = API_KEY

        // Using the client, perform the HTTP request
        client[
                "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US",
                param,
                object : JsonHttpResponseHandler()

                {
                    /*
                     * The onSuccess function gets called when
                     * HTTP response status is "200 OK"
                     */
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        //TODO - Parse JSON into Models
                        val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray

                        var models:List<TopMovie> = ArrayList()
                        for (i in 0 until resultsJSON.length()){
                            val movie_details = resultsJSON.getJSONObject(i)
                            val movie_title = movie_details.get("title").toString()
                            val movie_description = movie_details.get("overview").toString()
                            val movie_imageURL = movie_details.get("poster_path").toString()
                            models+= TopMovie(movie_title,movie_description,movie_imageURL)
                        }

//                        val movieRawJSON : String = resultsJSON.get("movies").toString()
//
//                        val gson = Gson()
//                        val arrayBookType = object : TypeToken<List<TopMovie>>() {}.type
//
//
//                        val model : List<TopMovie> = gson.fromJson(movieRawJSON,arrayBookType) // Fix me!
                        recyclerView.adapter = TopMovieRecyclerViewAdapter(models, this@TopMovieFragment)

                        // Look for this in Logcat:
                        Log.d("TopMovieFragment", "response successful")
                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("TopMovieFragment", errorResponse)
                        }
                    }
                }]

    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: TopMovie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}