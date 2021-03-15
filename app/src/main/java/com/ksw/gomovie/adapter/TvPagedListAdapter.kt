package com.ksw.gomovie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksw.gomovie.R
import com.ksw.gomovie.activity.TvDetailActivity
import com.ksw.gomovie.databinding.ItemMovieListBinding
import com.ksw.gomovie.databinding.ItemNetworkStateBinding
import com.ksw.gomovie.model.main.TV
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL

/**
 * Created by KSW on 2021-03-15
 */


class TvPagedListAdapter(private val context: Context) :
    PagedListAdapter<TV, RecyclerView.ViewHolder>(TVDiffCallback()) {

    val POPULAR_TV_VIEW_TYPE = 1
    private val NETWORK_VIEW_TYPE = 2
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        lateinit var view: View
        return if (viewType == POPULAR_TV_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.item_movie_list, parent, false)
            TVItemVieHolder(
                ItemMovieListBinding.bind(view)
            )
        } else {
            view = layoutInflater.inflate(R.layout.item_network_state, parent, false)
            NetworkStateItemViewHolder(
                ItemNetworkStateBinding.bind(view)
            )
        }
    }

    class TVItemVieHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTvData(tv: TV?, context: Context) {
            binding.tvMovieTitle.text = tv?.name
            binding.tvRating.text = "⭐ " + tv?.voteAverage.toString()

            if (!tv?.posterPath.isNullOrEmpty()) {
                val postUrl = IMAGE_BASE_URL + tv?.posterPath
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
                val intent = Intent(context, TvDetailActivity::class.java)
                intent.putExtra("id", tv?.id)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == POPULAR_TV_VIEW_TYPE) {
            (holder as TVItemVieHolder).bindTvData(getItem(position), context)
        } else if (getItemViewType(position) == NETWORK_VIEW_TYPE) {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (hasAnyExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            POPULAR_TV_VIEW_TYPE
        }
    }

    private fun hasAnyExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasAnyExtraRow()) 1 else 0
    }

    class NetworkStateItemViewHolder(private val binding: ItemNetworkStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                binding.pbNetwork.visibility = View.VISIBLE
            } else {
                binding.pbNetwork.visibility = View.GONE
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                binding.tvNetwork.visibility = View.VISIBLE
                binding.tvNetwork.text = networkState.message
            } else if (networkState != null && networkState == NetworkState.END) {
                binding.tvNetwork.visibility = View.VISIBLE
                binding.tvNetwork.text = "리스트가 없습니다."
            } else {
                binding.tvNetwork.visibility = View.GONE
            }
        }

    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState: NetworkState? = this.networkState
        val hadExtraRow: Boolean = hasAnyExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow: Boolean = hasAnyExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(super.getItemCount() - 1)
        }
    }

    class TVDiffCallback : DiffUtil.ItemCallback<TV>() {
        override fun areItemsTheSame(oldItem: TV, newItem: TV): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TV, newItem: TV): Boolean {
            return oldItem == newItem
        }

    }


}

