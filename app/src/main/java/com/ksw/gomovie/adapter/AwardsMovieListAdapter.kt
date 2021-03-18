package com.ksw.gomovie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksw.gomovie.R
import com.ksw.gomovie.databinding.ItemAwardsListBinding
import com.ksw.gomovie.fragment.AwardMovieListFragment
import com.ksw.gomovie.model.main.FeaturedMovieItem

/**
 * Created by KSW on 2021-03-18
 */

class AwardsMovieListAdapter(
    private val awardsList: ArrayList<FeaturedMovieItem>,
    private val context: Context
) : RecyclerView.Adapter<AwardsMovieListAdapter.MovieHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_awards_list, parent, false)

        return MovieHolder(
            ItemAwardsListBinding.bind(view)
        )

    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val awardItems = awardsList[position]
        holder.bindList(awardItems, context)
    }

    override fun getItemCount(): Int {
        return awardsList.size
    }

    class MovieHolder(private val binding: ItemAwardsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindList(awardItems: FeaturedMovieItem, context: Context) {
            binding.tvAwardsText.text = awardItems.listName

            if (awardItems.listImage.isNotEmpty()) {
                val posterUrl = awardItems.listImage
                Glide.with(itemView.context)
                    .load(posterUrl)
                    .into(binding.ivAwardsImage)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivAwardsImage)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, AwardMovieListFragment::class.java)
                intent.putExtra("listId", awardItems.listId)
                context.startActivity(intent)
            }

        }


    }


}