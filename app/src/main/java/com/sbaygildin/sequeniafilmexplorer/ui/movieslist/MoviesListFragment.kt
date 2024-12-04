package com.sbaygildin.sequeniafilmexplorer.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sbaygildin.sequeniafilmexplorer.R
import com.sbaygildin.sequeniafilmexplorer.databinding.FragmentMoviesListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.progressContainer.visibility = View.VISIBLE
                binding.contentScrollView.visibility = View.GONE
            } else {
                binding.progressContainer.visibility = View.GONE
                binding.contentScrollView.visibility = View.VISIBLE
            }
        })

        val genresAdapter = GenresAdapter { genre ->
            viewModel.filterByGenre(genre)
        }
        binding.genresRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.genresRecyclerView.adapter = genresAdapter

        viewModel.genres.observe(viewLifecycleOwner, Observer { genres ->
            if (genres.isNotEmpty()) {
                binding.genresTitleTextView.visibility = View.VISIBLE
            } else {
                binding.genresTitleTextView.visibility = View.GONE
            }
            genresAdapter.submitList(genres)
            genresAdapter.setSelectedGenre(viewModel.getSelectedGenre())
        })

        val filmsAdapter = FilmsAdapter { film ->
            val action = MoviesListFragmentDirections
                .actionMoviesListFragmentToMovieDetailsFragment(film)
            findNavController().navigate(action)
        }
        binding.filmsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.filmsRecyclerView.adapter = filmsAdapter

        viewModel.films.observe(viewLifecycleOwner, Observer { films ->
            if (films.isNotEmpty()) {
                binding.filmsTitleTextView.visibility = View.VISIBLE
            } else {
                binding.filmsTitleTextView.visibility = View.GONE
            }
            filmsAdapter.submitList(films)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                Snackbar.make(binding.root,
                    getString(R.string.txt_network_error), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.btn_repeat)) {
                        viewModel.fetchFilms()
                    }
                    .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.selection_color))
                    .show()

            }
        })
        viewModel.fetchFilms()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



