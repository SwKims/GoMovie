package com.ksw.gomovie.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksw.gomovie.R
import com.ksw.gomovie.activity.MovieDetailActivity
import com.ksw.gomovie.databinding.ItemMovieListBinding
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL

/**
 * Created by KSW on 2021-02-23
 */

class MovieAdapter(private val movies: List<Movie>, private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val itemMovie = movies[position]
        holder.bind(itemMovie, context)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(movies: Movie, context: Context) {
            binding.tvMovieTitle.text = movies?.title
            binding.tvRating.text = "⭐ " + movies?.voteAverage.toString()

            if (movies.posterPath.isEmpty()) {
                val postUrl = IMAGE_BASE_URL + movies?.posterPath
                Glide.with(itemView.context)
                    .load(postUrl)
                    .into(binding.ivMovieImage)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivMovieImage)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("id", movies.id)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity?,
                    Pair(binding.movieCard, "imageTransition")
                )
                context.startActivity(intent, options.toBundle())
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            }


        }

        companion object {
            fun from(parent: ViewGroup): MovieHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieListBinding.inflate(layoutInflater, parent, false)
                return MovieHolder(binding)
            }
        }
    }


}