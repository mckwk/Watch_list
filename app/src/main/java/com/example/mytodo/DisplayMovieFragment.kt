package com.example.mytodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.data.IMPORTANCE
import com.example.mytodo.databinding.FragmentDisplayMovieBinding

class DisplayMovieFragment : Fragment() {
    val args: DisplayMovieFragmentArgs by navArgs()
    private lateinit var binding: FragmentDisplayMovieBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDisplayMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get the movie from the arguments and display the movie details
        val movie = args.movie
        binding.displayTitle.text = movie.title
        binding.displayDescription.text = movie.description
        // select the drawable resource for the image view based on the importance of the movie
        val importanceDrawable = when (movie.importance) {
            IMPORTANCE.LOW -> R.drawable.circle_drawable_green
            IMPORTANCE.NORMAL -> R.drawable.circle_drawable_orange
            IMPORTANCE.HIGH -> R.drawable.circle_drawable_red
        }
        binding.displayImportance.setImageResource(importanceDrawable)

        binding.displayEdit.setOnClickListener {
            // create an action to navigate to the AddMovieFragment with the displayed movie
            val actionEditMovie =
                DisplayMovieFragmentDirections.actionDisplayMovieFragmentToAddMovieFragment()
            // set the movie to edit and the edit flag to true in the action
            with(actionEditMovie) {
                movieToEdit = movie
                edit = true
            }
            // use the navigate method to perform the navigation action created above
            findNavController().navigate(actionEditMovie)
        }
    }
}

