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
import com.ksw.gomovie.activity.SeasonActivity
import com.ksw.gomovie.databinding.ItemTvSeasonListBinding
import com.ksw.gomovie.model.tv.Season
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KSW on 2021-03-16
 */

class TvSeasonListAdapter(
    private val tvId: Int,
    private val seasons: List<Season>,
    private val context: Context
) : RecyclerView.Adapter<TvSeasonListAdapter.TvSeasonHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvSeasonHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_tv_season_list, parent, false)
        return TvSeasonHolder(
            ItemTvSeasonListBinding.bind(view)
        )

    }

    override fun onBindViewHolder(holder: TvSeasonHolder, position: Int) {
        val seasonItem = seasons[position]
        holder.bindSeasons(seasonItem, context, tvId)
    }

    override fun getItemCount(): Int {
        return seasons.size
    }

    class TvSeasonHolder(private val binding: ItemTvSeasonListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindSeasons(seasons: Season, context: Context, tvId: Int) {
            binding.tvSeasonNumber.text = seasons.name
            binding.tvSeasonEpisodeCount.text = "총 "+ seasons.episodeCount.toString() + "회"

            if (seasons.airDate.isNotEmpty()) {
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val targetFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date: Date = originalFormat.parse(seasons.airDate)
                val formattedDate: String = targetFormat.format(date)
                binding.tvSeasonStartDay.text = "시작일 : $formattedDate"
            } else {
                binding.tvSeasonStartDay.text = "-"
            }

            if (seasons.posterPath.isNotEmpty()) {
                val posterUrl = IMAGE_BASE_URL + seasons.posterPath
                Glide.with(binding.root.context)
                    .load(posterUrl)
                    .into(binding.ivSeasonPoster)
            } else {
                Glide.with(binding.root.context)
                    .load(R.drawable.ic_error)
                    .into(binding.ivSeasonPoster)
            }

            itemView.setOnClickListener {

                val intent = Intent(context, SeasonActivity::class.java)
                intent.putExtra("seasonNumber", seasons.seasonNumber)
                intent.putExtra("tvId", tvId)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity?,
                    Pair(binding.ivSeasonPoster, "seasonPosterTransition")
                )
                context.startActivity(intent, options.toBundle())
            }

        }
    }
}