package com.ksw.gomovie.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.util.Pair
import com.ksw.gomovie.R
import com.ksw.gomovie.activity.TvDetailActivity
import com.ksw.gomovie.databinding.ItemMovieListBinding
import com.ksw.gomovie.model.main.TV
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL

/**
 * Created by KSW on 2021-03-19
 */

class TvAdapter(private val Tv: List<TV>, private val context: Context) :
    RecyclerView.Adapter<TvAdapter.TvHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_movie_list, parent, false)
        return TvHolder(
            ItemMovieListBinding.bind(view)
        )
    }

    override fun onBindViewHolder(holder: TvHolder, position: Int) {
        val tvList = Tv[position]
        holder.bindTv(tvList, context)
    }

    override fun getItemCount(): Int {
        return Tv.size
    }

    class TvHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindTv(tv: TV, context: Context) {
            binding.tvMovieTitle.text = tv.name
            binding.tvRating.text = "⭐ " + tv.voteAverage.toString()

            if (tv.posterPath.isNotEmpty()) {
                val posterUrl = IMAGE_BASE_URL + tv.posterPath
                Glide.with(binding.root.context)
                    .load(posterUrl)
                    .into(binding.ivMovieImage)
            } else {
                Glide.with(binding.root.context)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivMovieImage)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, TvDetailActivity::class.java)
                intent.putExtra("id", tv.id)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity,
                    Pair(binding.movieCard, "imageTransition")
                )
                context.startActivity(intent, options.toBundle())
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }

}