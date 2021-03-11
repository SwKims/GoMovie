package com.ksw.gomovie.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ksw.gomovie.R
import com.ksw.gomovie.database.FavoritesEntity
import com.ksw.gomovie.databinding.MovieFavoriteFragmentBinding

/**
 * Created by KSW on 2021-03-10
 */

class FavoriteMovieAdapter(
//    private val requireActivity: FragmentActivity,
//    private val favoriteMoviesViewModel: FavoriteMoviesViewModel
) : RecyclerView.Adapter<FavoriteMovieAdapter.MyViewHolder>(), ActionMode.Callback {

    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    private var multiSelection = false

    private var favoriteMovies = emptyList<FavoritesEntity>()
    private var selectedMovies = arrayListOf<FavoritesEntity>()
    private var myViewHolder = arrayListOf<MyViewHolder>()

    class MyViewHolder(private val binding: MovieFavoriteFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoriteEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieFavoriteFragmentBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        myViewHolder.add(holder)
        rootView = holder.itemView.rootView

        val currentMovie = favoriteMovies[position]
        holder.bind(currentMovie)

    }

    override fun getItemCount(): Int {
        return favoriteMovies.size
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorite_menu, menu)
        mActionMode = actionMode!!
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_movie) {
            selectedMovies.forEach {
//                favoriteMoviesViewModel.deleteFavoriteMovie(it)
            }
            showSnackBar("${selectedMovies.size} 삭제됨")
            multiSelection = false
            selectedMovies.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        multiSelection = false
        selectedMovies.clear()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") { }
            .show()
    }


}