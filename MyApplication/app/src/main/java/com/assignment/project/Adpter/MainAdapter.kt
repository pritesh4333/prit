package com.assignment.project.Adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.project.Model.BusinessListModel
import com.assignment.project.Model.sample
import com.assignment.project.R
import com.bumptech.glide.Glide
import com.assignment.project.databinding.LayoutRvItemBinding;

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var movies = mutableListOf<sample>()

    fun setMovieList(movies: java.util.ArrayList<sample>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = LayoutRvItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.movieTitle.text = movie.businesses[position].name
        Glide.with(holder.itemView.context).load(movie.businesses.get(position).image_url).placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.moviePoster)

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

class MainViewHolder(val binding: LayoutRvItemBinding) : RecyclerView.ViewHolder(binding.root) {}