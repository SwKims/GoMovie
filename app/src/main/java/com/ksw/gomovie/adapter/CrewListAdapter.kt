package com.ksw.gomovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksw.gomovie.R
import com.ksw.gomovie.databinding.ItemCrewBinding
import com.ksw.gomovie.model.detail.CrewDetail
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL

/**
 * Created by KSW on 2021-02-26
 */

class CrewListAdapter(private val crew: ArrayList<CrewDetail>, private val context: Context) :
    RecyclerView.Adapter<CrewListAdapter.CrewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_crew, parent, false)
        return CrewHolder(
            ItemCrewBinding.bind(view)
        )
    }

    override fun onBindViewHolder(holder: CrewHolder, position: Int) {
        val crewItem = crew[position]
        holder.bindCrew(crewItem, context)
    }

    override fun getItemCount(): Int {
        return crew.size
    }


    class CrewHolder(private val binding: ItemCrewBinding) : RecyclerView.ViewHolder(binding.root) {

        private var crewItem: CrewDetail?= null
        private lateinit var context: Context

        fun bindCrew(crewItem: CrewDetail, context: Context) {
            this.crewItem = crewItem
            this.context = context

            binding.tvCrewName.text = crewItem.name
            binding.tvCrewCharacterName.text = crewItem.job

            if (crewItem.profilePath?.isNotEmpty() == true) {
                val profileUrl = IMAGE_BASE_URL + crewItem.profilePath
                Glide.with(binding.root)
                    .load(profileUrl)
                    .into(binding.ivCastImage)
            } else {
                Glide.with(binding.root)
                    .load(R.drawable.ic_error)
                    .into(binding.ivCastImage)
            }

            

        }

    }



}