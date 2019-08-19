package com.emi.nollyfilms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.emi.nollyfilms.R
import com.emi.nollyfilms.databinding.ContentTrailerBinding
import com.emi.nollyfilms.model.Reviews
import com.emi.nollyfilms.model.Trailers
import com.emi.nollyfilms.vm.DetailViewPresenter

class VideoAdapter constructor(private var trailers : MutableList<Trailers>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var reviews = Reviews()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding : ContentTrailerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.content_trailer, parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
         return holder.bind(trailers[position])
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    override fun getItemViewType(position: Int) = position

    fun updateVideo(list : MutableList<Trailers>){
        this.trailers = list
        notifyDataSetChanged()
    }


    inner class VideoViewHolder(val binding : ContentTrailerBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(trailers: Trailers){
            if(binding.detailViewPresenter == null){
                binding.detailViewPresenter = DetailViewPresenter(
                    context = itemView.context,
                    trailers = trailers,
                    review = reviews)
                binding.executePendingBindings()
            }else{
                binding.detailViewPresenter!!.trailers = trailers
            }
        }
    }

}