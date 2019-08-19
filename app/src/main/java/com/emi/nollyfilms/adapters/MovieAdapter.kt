package com.emi.nollyfilms.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emi.nollyfilms.R
import com.emi.nollyfilms.databinding.ContentMainBinding
import com.emi.nollyfilms.model.Movies
import com.emi.nollyfilms.vm.PresenterViewModel
import javax.inject.Inject

class MovieAdapter : PagedListAdapter<Movies, MovieAdapter.MovieViewHolder>(movieDiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding : ContentMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.content_main, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie : Movies? = getItem(position)
        if(movie != null && itemCount > 0){
        holder.bind(movie)
        }
    }

    override fun getItemViewType(position: Int) = position


    inner class MovieViewHolder(private val binding : ContentMainBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie : Movies){
            if(binding.movieModel == null){
                binding.movieModel = PresenterViewModel(itemView.context, movie)
                binding.executePendingBindings()
            }else{
                binding.movieModel!!.movies = movie
                binding.executePendingBindings()
            }
        }
    }

    companion object{
        var movieDiffCallback = object : DiffUtil.ItemCallback<Movies>(){
            override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}