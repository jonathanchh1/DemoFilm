package com.emi.nollyfilms.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.emi.nollyfilms.R
import com.emi.nollyfilms.adapters.ReviewAdapter
import com.emi.nollyfilms.adapters.VideoAdapter
import com.emi.nollyfilms.databinding.DetailFragmentBinding
import com.emi.nollyfilms.model.Movies
import com.emi.nollyfilms.vm.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*

import android.util.Log
import com.emi.nollyfilms.di.injector
import com.emi.nollyfilms.ui.VideoActivity


class DetailFragment : Fragment(){

    private lateinit var detailView : DetailViewModel
    private lateinit var videoAdapter : VideoAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var binding : DetailFragmentBinding
    private lateinit var LinearlayoutReview : LinearLayoutManager
    private lateinit var linearLayoutVideo : LinearLayoutManager
    private lateinit var movies: Movies
    private var key : String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movies = DetailMovies(activity?.intent!!, context!!)
        detailView = ViewModelProviders.of(this, injector.detailViewModelFactory()).get(DetailViewModel::class.java)
        videoAdapter = VideoAdapter(mutableListOf())
        reviewAdapter = ReviewAdapter(mutableListOf())
        LinearlayoutReview= LinearLayoutManager(context)
        linearLayoutVideo = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        initalizeView()

        if(movies != null){
            queryIds(movies.id.toString())
            Log.d(DetailFragment::class.java.simpleName, "${movies.id}, movie id for trailers/reviews")
        }

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         binding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.detail_fragment, container, false)
        binding.detailViewModel = detailView
        binding.activity = this
        binding.lifecycleOwner = this
        binding.trailerList.adapter = videoAdapter
        binding.trailerList.layoutManager = linearLayoutVideo
        binding.trailerList.setHasFixedSize(true)
        binding.trailerList.isNestedScrollingEnabled = true
        binding.reviewList.adapter = reviewAdapter
        binding.reviewList.layoutManager = LinearlayoutReview
        return binding.root
    }

    private fun initalizeView(){
        detailView.streamReviews.observe(this, Observer {
             reviewAdapter.updateReviews(it)
             reviewAdapter.notifyDataSetChanged()
        })

        detailView.streamTrailers.observe(this, Observer {
            if (it != null) {
                videoAdapter.updateVideo(it)
                videoAdapter.notifyDataSetChanged()
                button_watch_trailer.isEnabled = true
                it.map {
                    key = it.key!!
                }
            }
        })
    }


    fun onWatch(){
        if(key != null) {
            val intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(getString(R.string.videourl), key)
            startActivity(intent)
        }
    }

    private fun queryIds(id : String?){
        detailView.onRequestReviewData(id)
        detailView.onRequestTrailerData(id)
    }



    fun release() = movies.release
    fun detailThumb() = movies.detail_thumbnail
    fun originalTitle() = movies.title
    fun VoteAverage() = movies.vote_average
    fun desc() = movies.overview
    fun isAdult() : Boolean{ return movies.Isadult!!}


    companion object{
        fun newInstance() : DetailFragment{
            return DetailFragment()
        }

        fun DetailMovies(intent : Intent, context: Context) : Movies{
            return intent.getParcelableExtra(context.getString(R.string.movies)) as Movies
        }
    }
}