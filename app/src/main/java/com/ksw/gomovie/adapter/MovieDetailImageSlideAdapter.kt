package com.ksw.gomovie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ksw.gomovie.R
import com.ksw.gomovie.databinding.ItemImageSliderBinding
import com.ksw.gomovie.model.detail.Image
import com.ksw.gomovie.util.Constants.Companion.MOVIE_IMAGE_URL
import com.smarteist.autoimageslider.SliderViewAdapter

/**
 * Created by KSW on 2021-02-26
 */
class MovieDetailImageSlideAdapter(private val images: List<Image>) :
    SliderViewAdapter<MovieDetailImageSlideAdapter.ImageSlide>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup): ImageSlide {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_image_slider, parent, false)
        return ImageSlide(
            ItemImageSliderBinding.bind(view)
        )

    }

    override fun onBindViewHolder(viewHolder: ImageSlide, position: Int) {
        val imageMovie = images[position]
        viewHolder.bind(imageMovie)
    }

    override fun getCount(): Int {
        return images.size
    }

    class ImageSlide(private val binding: ItemImageSliderBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {

        fun bind(image: Image) {
            Glide.with(binding.root.context)
                .load(MOVIE_IMAGE_URL + image.filePath)
                .error(R.drawable.ic_error)
                .into(binding.ivMovieImageSlider)
        }

    }


}

