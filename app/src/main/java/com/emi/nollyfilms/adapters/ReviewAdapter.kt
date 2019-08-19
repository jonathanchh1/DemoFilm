package com.emi.nollyfilms.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.emi.nollyfilms.R
import com.emi.nollyfilms.databinding.ReviewContentBinding
import com.emi.nollyfilms.model.Reviews
import com.emi.nollyfilms.model.Trailers
import com.emi.nollyfilms.vm.DetailViewModel
import com.emi.nollyfilms.vm.DetailViewPresenter

class ReviewAdapter constructor(private var reviewList : MutableList<Reviews>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){

   private val trailers = Trailers()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding : ReviewContentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.review_content, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        return holder.bind(reviewList[position])
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun getItemViewType(position: Int) = position

    fun updateReviews(list : MutableList<Reviews>){
        this.reviewList = list
    }

    inner class ReviewViewHolder(val binding : ReviewContentBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(reviews: Reviews){
            if(binding.detailViewPresenter == null){
                binding.detailViewPresenter = DetailViewPresenter(
                    context = itemView.context,
                    trailers = trailers,
                    review = reviews
                )
            }else{
                binding.detailViewPresenter!!.review = reviews
            }
        }
    }
}