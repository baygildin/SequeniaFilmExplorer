package com.sbaygildin.sequeniafilmexplorer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.sbaygildin.sequeniafilmexplorer.databinding.ActivityMainBinding
import com.sbaygildin.sequeniafilmexplorer.model.Film


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbarLayout.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController

        navController?.let {
            it.addOnDestinationChangedListener { _, destination, arguments ->
                when (destination.id) {
                    R.id.moviesListFragment -> {
                        binding.toolbarLayout.toolbarTitle.text = getString(R.string.txt_films)
                        binding.toolbarLayout.backButton.visibility = View.GONE
                    }
                    R.id.movieDetailsFragment -> {
                        val film = arguments?.getParcelable<Film>(getString(R.string.film))
                        binding.toolbarLayout.toolbarTitle.text = film?.name
                            ?: getString(R.string.txt_details)
                        binding.toolbarLayout.backButton.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }
}
