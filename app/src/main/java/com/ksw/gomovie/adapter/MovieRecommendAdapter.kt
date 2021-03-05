package com.ksw.gomovie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksw.gomovie.R
import com.ksw.gomovie.activity.MovieDetailActivity
import com.ksw.gomovie.databinding.ItemMovierecommendListBinding
import com.ksw.gomovie.model.detail.Similar
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.model.main.MovieDetail
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL
import com.ksw.gomovie.util.Constants.Companion.MOVIE_IMAGE_URL
import com.ksw.gomovie.util.Constants.Companion.MOVIE_TYPE

/**
 * Created by KSW on 2021-03-04
 */

class MovieRecommendAdapter(
    private val recommend: MutableList<Similar>,
    private val type: String,
    private val context: Context
) : RecyclerView.Adapter<MovieRecommendAdapter.RecommendHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_movierecommend_list, parent, false)
        return RecommendHolder(
            ItemMovierecommendListBinding.bind(view)
        )
    }

    override fun onBindViewHolder(holder: RecommendHolder, position: Int) {
        val recommendItem = recommend[position]
        holder.bindRecommend(recommendItem, context)
    }

    override fun getItemCount(): Int {
        return recommend.size
    }

    fun setSimilarList(similar: List<Similar>) {
        this.recommend.clear()
        this.recommend.addAll(similar)
        notifyDataSetChanged()
    }

    inner class RecommendHolder(private val binding: ItemMovierecommendListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindRecommend(similar: Similar, context: Context) {

            if (type == MOVIE_TYPE) {
                binding.tvRecommendTitle.text = similar.title
            }

            binding.tvRecommendRating.text = similar.rating.toString()

            Glide.with(itemView)
                .load(MOVIE_IMAGE_URL + similar.posterPath)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(binding.ivRecommend)

            itemView.setOnClickListener {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("id", similar?.id)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }

        }

    }

}