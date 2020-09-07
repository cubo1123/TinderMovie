package com.cubo1123.movie.tinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.cubo1123.movie.tinder.R
import com.cubo1123.movie.tinder.databinding.FragmentProfileBinding
import com.cubo1123.movie.tinder.viewModels.MovieProfileViewModel
import timber.log.Timber

class MovieProfileFragment : Fragment() {
    val args: MovieProfileFragmentArgs by navArgs()
    private val viewModel: MovieProfileViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, MovieProfileViewModel.Factory(activity.application,args.movieId)).get(MovieProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false)
        Timber.d("el id a mostrar es : ${args.movieId}")
        viewModel.updateMovie()
        return binding.root
    }
}