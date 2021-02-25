package com.ksw.gomovie.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksw.gomovie.R
import com.ksw.gomovie.databinding.ItemVideoListBinding
import com.ksw.gomovie.model.detail.VideoDetail
import com.ksw.gomovie.util.Constants.Companion.TRAILER_THUMBNAIL_BASE_URL
import com.ksw.gomovie.util.Constants.Companion.TRAILER_THUMBNAIL_END_URL

/**
 * Created by KSW on 2021-02-25
 */

class TrailerListAdapter(private val video: ArrayList<VideoDetail>, private val context: Context) :
    RecyclerView.Adapter<TrailerListAdapter.TrailerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_video_list, parent, false)
        return TrailerHolder(
            ItemVideoListBinding.bind(view)
        )
    }

    override fun onBindViewHolder(holder: TrailerHolder, position: Int) {
        val videoList = video[position]
        holder.bindVideos(videoList, context)
    }

    override fun getItemCount(): Int {
        return video.size
    }

    class TrailerHolder(private val binding: ItemVideoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var video: VideoDetail? = null
        private lateinit var context: Context
        lateinit var key: String

        fun bindVideos(videoList: VideoDetail, context: Context) {
            this.video = videoList
            this.context = context

            key = video!!.key
            binding.tvTrailerName.text = video!!.name

            Glide.with(binding.root)
                .load(TRAILER_THUMBNAIL_BASE_URL + key + TRAILER_THUMBNAIL_END_URL)
                .into(binding.ivTrailer)

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://www.youtube.com/watch?v=$key")
                intent.setPackage("com.google.android.youtube")
                context.startActivity(intent)
            }


        }

    }


}