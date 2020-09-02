package com.cubo1123.movie.tinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.cubo1123.movie.tinder.R
import com.cubo1123.movie.tinder.adapters.SwipeCardAdapter
import com.cubo1123.movie.tinder.database.getDatabase
import com.cubo1123.movie.tinder.databinding.FragmentPickerBinding
import com.cubo1123.movie.tinder.domain.MovieProfile
import com.cubo1123.movie.tinder.viewModels.PickerViewModel
import com.yuyakaido.android.cardstackview.*
import timber.log.Timber

class PickerFragment : Fragment(), CardStackListener {

    private lateinit var binding: FragmentPickerBinding
    private lateinit var adapter: SwipeCardAdapter
    private lateinit var manager : CardStackLayoutManager
    private val viewModel: PickerViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, PickerViewModel.Factory(activity.application)).get(PickerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_picker,container,false)
        binding.lifecycleOwner = this
        manager = CardStackLayoutManager(context,this)
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())

        binding.swipeCard.layoutManager = manager
        adapter = SwipeCardAdapter(SwipeCardAdapter.PickerProfileListener { item ->
            Timber.d(item.toString())
        })

        binding.swipeCard.adapter = adapter
        binding.swipeCard.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.movies.observe(viewLifecycleOwner, Observer<List<MovieProfile>>{
            Timber.d("data")
            Timber.d(it.toString())
            adapter.submitList(it)

        })
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Timber.d("CardStackViewonCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Timber.d("CardStackViewonCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            //paginate()
        }
    }

//    private fun paginate() {
//        val old = adapter.profiles
//        val new = old.plus(createSpots())
//        val callback = SpotDiffCallback(old, new)
//        val result = DiffUtil.calculateDiff(callback)
//        adapter.setSpots(new)
//        result.dispatchUpdatesTo(adapter)
//    }

    override fun onCardCanceled() {
        Timber.d("Canceled")
    }

    override fun onCardAppeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_name)
        Timber.d("CardStackViewonCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_name)
        Timber.d("CardStackViewonCardDisappeared: ($position) ${textView.text}")
    }

    override fun onCardRewound() {
        Timber.d("card rewound")
    }
}