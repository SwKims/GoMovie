package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksw.gomovie.R
import com.ksw.gomovie.databinding.CastAboutFragmentBinding
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.cast.CastDetailRepository
import com.ksw.gomovie.viewmodel.CastDetailViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KSW on 2021-03-05
 */

class CastAboutFragment(private val castId: Int): Fragment() {

    private lateinit var binding: CastAboutFragmentBinding

    lateinit var castDetailRepository: CastDetailRepository
    private lateinit var castDetailViewModel: CastDetailViewModel
    lateinit var linearLayoutManager: LinearLayoutManager

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

        return binding.root
    }

    private fun getViewModel(castId: Int): CastDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CastDetailViewModel(castDetailRepository, castId) as T
            }
        })[CastDetailViewModel::class.java]


    }

}