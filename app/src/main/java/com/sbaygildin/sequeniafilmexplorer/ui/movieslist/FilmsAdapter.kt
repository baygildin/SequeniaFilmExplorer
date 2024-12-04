package com.sbaygildin.sequeniafilmexplorer.ui.movieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sbaygildin.sequeniafilmexplorer.R
import com.sbaygildin.sequeniafilmexplorer.databinding.ItemFilmBinding
import com.sbaygildin.sequeniafilmexplorer.model.Film

class FilmsAdapter(private val onClick: (Film) -> Unit) :
    RecyclerView.Adapter<FilmsAdapter.FilmViewHolder>() {

    private val films = mutableListOf<Film>()

    fun submitList(newFilms: List<Film>) {
        films.clear()
        films.addAll(newFilms)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films[position])
    }

    override fun getItemCount(): Int = films.size

    class FilmViewHolder(
        private val binding: ItemFilmBinding,
        private val onClick: (Film) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            binding.filmTitleTextView.text = film.localized_name
            Glide.with(binding.filmPosterImageView.context)
                .load(film.image_url)
                .placeholder(R.drawable.place_holder)
                .into(binding.filmPosterImageView)
            binding.root.setOnClickListener { onClick(film) }
        }
    }
}
