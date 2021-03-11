package com.ksw.gomovie.fragment

import android.Manifest.permission.CALL_PHONE
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ksw.gomovie.R
import com.ksw.gomovie.databinding.InfoBinding
import java.lang.Exception
import java.util.jar.Manifest

/**
 * Created by KSW on 2021-03-11
 */

class InfoFragment : Fragment() {

    private lateinit var binding: InfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = InfoBinding.inflate(inflater, container, false)

        binding.github.setOnClickListener {

            val profile = "https://github.com/swkims"
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(profile))
                intent.setPackage("com.ksw.android")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(profile)))
            }

        }

        binding.message.setOnClickListener {
            val call = Uri.parse("sms:010-2975-5126")
            val callIntent = Intent(Intent.ACTION_SENDTO, call)
            startActivity(callIntent)
        }

        binding.mail.setOnClickListener {

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto: ksw512649@gmail.com")
            intent.putExtra(Intent.EXTRA_EMAIL, "test")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hello World")

            startActivity(intent)
        }





        return binding.root
    }


}