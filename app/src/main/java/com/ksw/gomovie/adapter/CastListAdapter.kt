package com.ksw.gomovie.adapter

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
import com.ksw.gomovie.activity.CastDetailActivity
import com.ksw.gomovie.databinding.ItemCastBinding
import com.ksw.gomovie.model.detail.CastDetail
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL

/**
 * Created by KSW on 2021-02-26
 */

class CastListAdapter(private val cast: ArrayList<CastDetail>, private val context: Context) :
    RecyclerView.Adapter<CastListAdapter.CastHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_cast, parent, false)
        return CastHolder(
            ItemCastBinding.bind(view)
        )

    }

    override fun onBindViewHolder(holder: CastHolder, position: Int) {
        val castItem = cast[position]
        holder.bindCast(castItem, context)
    }

    override fun getItemCount(): Int {
        return cast.size
    }

    class CastHolder(private val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root) {

        private var castItem: CastDetail? = null
        private lateinit var context: Context

        fun bindCast(castItem: CastDetail, context: Context) {
            this.castItem = castItem
            this.context = context

            binding.tvCastName.text = castItem.name
            binding.tvCastCharacterName.text = castItem.character

            if (castItem.profilePath?.isNotEmpty() == true) {
                val profileUrl = IMAGE_BASE_URL + castItem.profilePath
                Glide.with(binding.root)
                    .load(profileUrl)
                    .into(binding.ivCastImage)
            } else {
                Glide.with(binding.root)
                    .load(R.drawable.ic_error)
                    .into(binding.ivCastImage)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, CastDetailActivity::class.java)
                intent.putExtra("id", castItem.id)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity?,
                    Pair(binding.ivCastImage, "peopleImage")
                )
                context.startActivity(intent, options.toBundle())
            }
        }

    }

}