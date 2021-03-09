package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ksw.gomovie.databinding.NetworkErrorBinding

/**
 * Created by KSW on 2021-02-23
 */

class ErrorFragment: Fragment() {

    /*private var _binding: NetworkErrorBinding?= null
    private val binding get() = _binding!!*/
    private lateinit var binding: NetworkErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = NetworkErrorBinding.inflate(inflater, container, false)


        return binding.root

    }
}