package com.cubo1123.movie.tinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.cubo1123.movie.tinder.R
import com.cubo1123.movie.tinder.databinding.FragmentProfileBinding
import timber.log.Timber

class ProfileFragment : Fragment() {
    val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false)
        Timber.d("el id a mostrar es : ${args.movieId}")
        return binding.root
    }
}