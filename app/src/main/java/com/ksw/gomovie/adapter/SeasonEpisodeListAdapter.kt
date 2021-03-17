package com.ksw.gomovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ksw.gomovie.R
import com.ksw.gomovie.databinding.ItemTvEpisodeListBinding
import com.ksw.gomovie.model.tv.Episode
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KSW on 2021-03-16
 */

class SeasonEpisodeListAdapter(
    private val episodes: List<Episode>,
    private val context: Context
) : RecyclerView.Adapter<SeasonEpisodeListAdapter.SeasonEpisodeListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            SeasonEpisodeListHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_tv_episode_list, parent, false)
        return SeasonEpisodeListHolder(
            ItemTvEpisodeListBinding.bind(view)
        )

    }

    override fun onBindViewHolder(
        holder: SeasonEpisodeListHolder,
        position: Int
    ) {
        val episodeList = episodes[position]
        holder.bindList(episodeList, context)
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    class SeasonEpisodeListHolder(private val binding: ItemTvEpisodeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindList(episode: Episode, context: Context) {
            binding.tvEpisodeNumber.text = episode.episodeNumber.toString()
            if (episode.name.isNotEmpty()) {
                binding.tvEpisodeName.text = episode.name
            }

            binding.tvEpisodeNumber.text = episode.episodeNumber.toString()

            if (episode.airDate.isNotEmpty()) {
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val targetFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date: Date = originalFormat.parse(episode.airDate)
                val formattedDate: String = targetFormat.format(date)
                binding.tvSeasonStartDay.text = formattedDate
            }


        }

    }

}