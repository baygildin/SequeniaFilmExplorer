package com.sbaygildin.sequeniafilmexplorer.ui.movieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sbaygildin.sequeniafilmexplorer.R

class GenresAdapter(private val onClick: (String?) -> Unit) :
    RecyclerView.Adapter<GenresAdapter.GenreViewHolder>() {

    private val genres = mutableListOf<String>()
    private var selectedGenre: String? = null

    fun submitList(newGenres: List<String>) {
        genres.clear()
        genres.addAll(newGenres)
        notifyDataSetChanged()
    }

    fun setSelectedGenre(genre: String?) {
        selectedGenre = genre
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genres[position]
        holder.bind(genre, genre == selectedGenre)
        holder.itemView.setOnClickListener {
            if (selectedGenre == genre) {
                setSelectedGenre(null)
                onClick(null)
            } else {
                setSelectedGenre(genre)
                onClick(genre)
            }
        }
    }

    override fun getItemCount() = genres.size

    class GenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val genreNameTextView: TextView = view.findViewById(R.id.genreNameTextView)
        fun bind(genre: String, isSelected: Boolean) {
            genreNameTextView.text = genre
            genreNameTextView.isSelected = isSelected
        }
    }
}


