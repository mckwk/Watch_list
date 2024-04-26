package com.example.mytodo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.data.STATUS
import com.example.mytodo.data.Movie
import com.example.mytodo.data.Movies
import com.example.mytodo.databinding.FragmentAddMovieBinding

class AddMovieFragment : Fragment() {
    val args: AddMovieFragmentArgs by navArgs()
    private lateinit var binding: FragmentAddMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize the binding variable
        binding = FragmentAddMovieBinding.inflate(inflater, container, false)
        // Set the title and description EditText fields with the movie to edit  }
        // (only if it's not null)
        binding.titleInput.setText(args.movieToEdit?.title)
        // Set the importance radio button with the movie to edit (only if it's not null)
        when (args.movieToEdit?.status) {
            STATUS.WATCHED -> binding.watchedRadioButton.isChecked = true
            STATUS.UNWATCHED -> binding.unwatchedRadioButton.isChecked = true
            else -> binding.unwatchedRadioButton.isChecked = true
        }
        return binding.root
    }   


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener { saveMovie() }
    }

    private fun saveMovie() {
        // Get the values from data fields on the screen
        var title: String = binding.titleInput.text.toString()
        val STATUS = when (binding.importanceGroup.checkedRadioButtonId) {
            R.id.unwatched_radioButton -> STATUS.UNWATCHED
            R.id.watched_radioButton -> STATUS.WATCHED
            else -> STATUS.UNWATCHED
        }
        // Handle missing EditText input
        if (title.isEmpty()) title = getString(R.string.default_movie_title) + "${Movies.list.size + 1}"
        // Create a new Movie item based on input values
        val movieItem = Movie(
            { title}.hashCode().toString(),
            title,
            title,
            STATUS
        )
        if(!args.edit) {
            // Add the new movie to the list of movies
            Movies.addMovie(movieItem)
        }else{
            // Update the movie to the list of movies
            Movies.updateMovie(oldMovie = args.movieToEdit,newMovie = movieItem)
        }


        // Hide the software keyboard with InputMethodManager
        val inputMethodManager: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

        // Navigate back to the MovieListFragment, the inclusive parameter is set to false
        findNavController().popBackStack(R.id.movieListFragment, false)
    }
}