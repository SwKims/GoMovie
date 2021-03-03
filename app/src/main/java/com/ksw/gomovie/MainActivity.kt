package com.ksw.gomovie

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView
import com.ksw.gomovie.databinding.ActivityMainBinding
import com.ksw.gomovie.fragment.ErrorFragment
import com.ksw.gomovie.fragment.MovieFragment
import com.ksw.gomovie.fragment.MovieSearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var transaction: FragmentTransaction
    lateinit var movieFragment: MovieFragment
    lateinit var errorFragment: ErrorFragment
    lateinit var searchFragment: MovieSearchFragment

    /*lateinit var toolbar: Toolbar
    lateinit var bottomBar: BubbleNavigationConstraintView*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val view = binding.root
        setContentView(view)*/

        movieFragment = MovieFragment()
        errorFragment = ErrorFragment()
        searchFragment = MovieSearchFragment()




        if (isNetworkAvailable()) {
            setFragment(movieFragment)
        } else {
            setFragment(errorFragment)
        }

        binding.bottomConstraint.setNavigationChangeListener{ view, position ->
            when(position) {
                0 -> {
                    if (isNetworkAvailable()) {
                        setFragment(movieFragment)
                    } else {
                        setFragment(errorFragment)
                    }
                }
                1 -> {
                    if (isNetworkAvailable()) {
                        setFragment(movieFragment)
                    } else {
                        setFragment(errorFragment)
                    }
                }
                2 -> {
                    if (isNetworkAvailable()) {
                        setFragment(movieFragment)
                    } else {
                        setFragment(errorFragment)
                    }
                }
                3 -> {
                    if (isNetworkAvailable()) {
                        setFragment(searchFragment)
                    } else {
                        setFragment(errorFragment)
                    }
                }
                4 -> {
                    if (isNetworkAvailable()) {
                        setFragment(movieFragment)
                    } else {
                        setFragment(errorFragment)
                    }
                }
                else -> {

                }
            }
        }
    }



    private fun setFragment(fragment: Fragment) {
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.tv_test, fragment)
        transaction.commit()

    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}