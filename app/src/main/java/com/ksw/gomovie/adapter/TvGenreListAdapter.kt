package com.ksw.gomovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ksw.gomovie.R
import com.ksw.gomovie.databinding.ItemGenresListBinding
import com.ksw.gomovie.model.main.Genre

/**
 * Created by KSW on 2021-03-15
 */

class TvGenreListAdapter(private val details: List<Genre>, private val context: Context) :
    RecyclerView.Adapter<TvGenreListAdapter.GenreHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_genres_list, parent, false)
        return GenreHolder(
            ItemGenresListBinding.bind(view)
        )

    }

    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        val genresList = details[position]
        holder.bindGenres(genresList, context)

    }

    override fun getItemCount(): Int {
        return details.size
    }

    class GenreHolder(private val binding: ItemGenresListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindGenres(genreList: Genre, context: Context) {
            binding.tvGenreName.text = genreList.name
        }

    }

}