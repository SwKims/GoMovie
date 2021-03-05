package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ksw.gomovie.R
import com.ksw.gomovie.databinding.CastAboutFragmentBinding
import com.ksw.gomovie.model.main.PeopleProfileImages
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.cast.CastDetailRepository
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL
import com.ksw.gomovie.viewmodel.CastDetailViewModel
import com.stfalcon.imageviewer.StfalconImageViewer
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KSW on 2021-03-05
 */

class CastAboutFragment(private val castId: Int) : Fragment() {

    private lateinit var binding: CastAboutFragmentBinding

    lateinit var castDetailRepository: CastDetailRepository
    private lateinit var castDetailViewModel: CastDetailViewModel
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var peopleProfiles: List<PeopleProfileImages>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CastAboutFragmentBinding.inflate(inflater, container, false)

        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()
        castDetailRepository = CastDetailRepository(
            apiServiceApi
        )

        castDetailViewModel = getViewModel(castId)

        castDetailViewModel.peopleDetails.observe(viewLifecycleOwner) {
            if (it.birthday.isNotEmpty()) {
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val targetFormat: DateFormat = SimpleDateFormat("yyyy년-MM월-dd일")
                val date: Date = originalFormat.parse(it.birthday)
                val formattedDate: String = targetFormat.format(date)
                binding.tvPeopleBirthdate.text = formattedDate
            } else {
                binding.tvPeopleBirthdate.text = "-"
            }
            if (it.placeOfBirth.isNotEmpty()) {
                binding.tvPeopleBirthplace.text = it.placeOfBirth
            } else {
                binding.tvPeopleBirthplace.text = "-"
            }

            binding.tvPeopleAlsoknow.text = it.knownForDepartment

        }

        castDetailViewModel.peopleImages.observe(viewLifecycleOwner) {
            peopleProfiles = it.profiles
            if (!peopleProfiles.isNullOrEmpty()) {
                binding.peopleImages.visibility = View.VISIBLE

                val profilePoster = peopleProfiles[0].filePath
                val postUrl: String = IMAGE_BASE_URL + profilePoster
                Glide.with(this)
                    .load(postUrl)
                    .into(binding.ivPeopleImages)

                if (peopleProfiles.size == 1) {
                    binding.tvImageCount.text = peopleProfiles.size.toString() + " Image"
                } else {
                    binding.tvImageCount.text = peopleProfiles.size.toString() + " Images"
                }

                binding.ivPeopleImages.setOnClickListener {
                    StfalconImageViewer.Builder(
                        activity, peopleProfiles
                    ) { posterImage: ImageView, profile: PeopleProfileImages ->
                        Glide.with(this)
                            .load(IMAGE_BASE_URL + profile.filePath)
                            .into(posterImage)
                    }
                        .withHiddenStatusBar(false)
                        .show()
                }
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivPeopleImages)
                binding.tvImageCount.text = "이미지가 없습니다."
            }

        }

        return binding.root
    }

    private fun getViewModel(castId: Int): CastDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CastDetailViewModel(castDetailRepository, castId) as T
            }
        })[CastDetailViewModel::class.java]


    }

}