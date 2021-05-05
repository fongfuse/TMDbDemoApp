package com.fongfuse.tmdbdemo.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fongfuse.tmdbdemo.R
import com.fongfuse.tmdbdemo.databinding.ViewMovieItemBinding
import com.fongfuse.tmdbdemo.extension.inflate
import com.fongfuse.tmdbdemo.extension.setImageUrl
import com.fongfuse.tmdbdemo.model.Movie

class MainAdapter(val context: Context) : RecyclerView.Adapter<MainAdapter.MovieListViewHolder>() {

    private var items = mutableListOf<Movie>()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MainAdapter.MovieListViewHolder {
        val view = parent.inflate(R.layout.view_movie_item)
        return MovieListViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
            holder: MainAdapter.MovieListViewHolder,
            position: Int
    ) {
        holder.bind(items[position])
    }

    fun setItems(data: MutableList<Movie>) {
        this.items = data
        notifyDataSetChanged()
    }

    inner class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewMovieItemBinding.bind(itemView)

        fun bind(data: Movie) {
            binding.apply {
                imgItem.setImageUrl(data.poster_path)
                tvDesc.text = data.overview ?: ""
                tvTitle.text = data.title ?: ""
                tvVote.text = String.format("%s : %s", "Score", data.vote_average.toString())
            }
        }
    }
}