package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import com.ksw.gomovie.adapter.MovieSearchAdapter
import com.ksw.gomovie.databinding.MovieSearchFragmentBinding
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.model.response.MovieResponse
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

/**
 * Created by KSW on 2021-03-03
 */
class MovieSearchFragment : Fragment() {

    /*private var _binding: MovieSearchFragmentBinding? = null
    private val binding get() = _binding!!*/
    private lateinit var binding: MovieSearchFragmentBinding

    private lateinit var searchAdapter: MovieSearchAdapter
    private val disposable = CompositeDisposable()
    private val publishSubject = PublishSubject.create<String>()
    var searchMovieList = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchAdapter = MovieSearchAdapter(
            searchMovieList,
            requireContext()
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MovieSearchFragmentBinding.inflate(inflater, container, false)
        val linearLayoutManager = GridLayoutManager(activity, 2)
        linearLayoutManager.reverseLayout = false
        binding.rvSearchView.layoutManager = linearLayoutManager
        binding.rvSearchView.setHasFixedSize(true)
        binding.rvSearchView.adapter = searchAdapter
        settingSearchData()

        return binding.root


    }


    private fun settingSearchData() {
        val apiService: MovieServiceApi = NetworkModule.getClient()
        val observer: DisposableObserver<MovieResponse> = getSearchObserver()

        disposable.add(
            publishSubject.debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMapSingle(object : Single<MovieResponse>(),
                    Function<String, SingleSource<MovieResponse>> {
                    @Throws(Exception::class)
                    override fun apply(data: String): Single<MovieResponse> {
                        return apiService.getSearchResults(data)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                    }

                    override fun subscribeActual(observer: SingleObserver<in MovieResponse>) {

                    }
                })
                .subscribeWith(observer)
        )

        disposable.add(observer)

        if (binding.etMovieSearch.text.isNullOrEmpty()) {
            searchMovieList.clear()
            searchAdapter.notifyDataSetChanged()

            RxTextView.textChangeEvents(binding.etMovieSearch)
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContactsTextWatcher())?.let {
                    disposable.add(
                        it
                    )
                }
        } else {
            publishSubject.onNext("")
        }
    }

    private fun getSearchObserver(): DisposableObserver<MovieResponse> {
        return object : DisposableObserver<MovieResponse>() {
            override fun onNext(movies: MovieResponse) {
                searchMovieList.clear()
                searchMovieList.addAll(movies.movieList)
                searchAdapter.notifyDataSetChanged()
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
                publishSubject.onNext("")
            }
        }
    }

    private fun searchContactsTextWatcher(): DisposableObserver<TextViewTextChangeEvent?>? {
        return object : DisposableObserver<TextViewTextChangeEvent?>() {
            override fun onNext(textViewTextChangeEvent: TextViewTextChangeEvent) {
                if (textViewTextChangeEvent.text().isNullOrBlank() || textViewTextChangeEvent.text()
                        .isNullOrEmpty()
                ) {
                    searchMovieList.clear()
                    searchAdapter.notifyDataSetChanged()
                } else {
                    publishSubject.onNext(textViewTextChangeEvent.text().toString())
                }
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }

        }
    }


    override fun onPause() {
        disposable.clear()
        searchMovieList.clear()
        binding.etMovieSearch.setText("")
        super.onPause()
    }


}