package com.sbaygildin.sequeniafilmexplorer.ui.moviedetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sbaygildin.sequeniafilmexplorer.R
import com.sbaygildin.sequeniafilmexplorer.databinding.FragmentMovieDetailsBinding
import com.sbaygildin.sequeniafilmexplorer.model.Film
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val film = args.film
        viewModel.setFilm(film)

        bindFilmData(film)
    }

    private fun bindFilmData(film: Film) {
        binding.filmTitleTextView.text = film.localized_name
        binding.filmGenresYearTextView.text = "${film.genres.joinToString(", ")} • ${film.year}"
        binding.filmRatingTextView.text = film.rating?.let {
            getString(R.string.rating_kinopoisk, String.format("%.1f", it))
        } ?: getString(R.string.n_a_rating_kinopoisk)
        binding.filmDescriptionTextView.text = film.description

        Glide.with(this)
            .load(film.image_url)
            .placeholder(R.drawable.place_holder)
            .into(binding.filmPosterImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
