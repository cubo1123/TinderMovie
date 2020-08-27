package com.cubo1123.movie.tinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.cubo1123.movie.tinder.R
import com.cubo1123.movie.tinder.databinding.FragmentMatchBinding
import com.cubo1123.movie.tinder.databinding.FragmentPickerBinding
import com.cubo1123.movie.tinder.viewModels.LikedViewModel

class MatchFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentMatchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match,container,false)
        return binding.root
    }
}