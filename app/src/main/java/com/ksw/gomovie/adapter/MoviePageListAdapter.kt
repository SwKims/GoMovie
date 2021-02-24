package com.ksw.gomovie.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksw.gomovie.R
import com.ksw.gomovie.databinding.ItemMovieListBinding
import com.ksw.gomovie.databinding.ItemNetworkStateBinding
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL

/**
 * Created by KSW on 2021-02-23
 */

class MoviePageListAdapter(private val context: Context) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val POPULAR_MOVIE_VIEW_TYPE = 1
    private val NETWORK_VIEW_TYPE = 2
    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        lateinit var view: View
        return if (viewType == POPULAR_MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.item_movie_list, parent, false)
            MovieItemVieHolder(
                ItemMovieListBinding.bind(view)
            )
        } else {
            view = layoutInflater.inflate(R.layout.item_network_state, parent, false)
            NetworkStateItemViewHolder(
                ItemNetworkStateBinding.bind(view)
            )
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == POPULAR_MOVIE_VIEW_TYPE) {
            (holder as MovieItemVieHolder).bindMovieData(getItem(position), context)
        } else if (getItemViewType(position) == NETWORK_VIEW_TYPE) {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }

    }

    private fun hasAnyExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasAnyExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasAnyExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            POPULAR_MOVIE_VIEW_TYPE
        }
    }

    class MovieItemVieHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindMovieData(movie: Movie?, context: Context) {

            binding.tvMovieTitle.text = movie?.title
            binding.tvRating.text = "\uD83C\uDF1F " + movie?.voteAverage.toString()

            if (!movie?.posterPath.isNullOrEmpty()) {
                val posterUrl = IMAGE_BASE_URL + movie?.posterPath
                Glide.with(itemView.context)
                    .load(posterUrl)
                    .into(binding.ivMovieImage)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivMovieImage)
            }

            /*itemView.setOnClickListener {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("id", movie?.id)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }*/

        }
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


    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

}

