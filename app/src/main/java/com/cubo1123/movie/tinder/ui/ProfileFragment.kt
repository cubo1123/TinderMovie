package com.cubo1123.movie.tinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.cubo1123.movie.tinder.R
import com.cubo1123.movie.tinder.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false)
        return binding.root
    }
}