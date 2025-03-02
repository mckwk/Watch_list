package com.example.mytodo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodo.data.Movies
import com.example.mytodo.data.STATUS
import com.example.mytodo.databinding.FragmentMovieListBinding
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

class MovieListFragment : Fragment(), ToDoListListener {
    // connect the fragment_movie_list.xml with MovieListFragment class
    private lateinit var binding: FragmentMovieListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        // Set the adapter and layout manager for the RecyclerView
        // "with" is a Kotlin extension function that allows you to call
        // the methods of an object without explicitly calling the object itself
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyMovieRecyclerViewAdapter(
                Movies.list,
                this@MovieListFragment,
                R.drawable.unchecked // Pass the drawable resource ID
            ) // adapter is responsible for displaying the data
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set the action for the FAB
        binding.addButton.setOnClickListener {
            // Navigate to the AddMovieFragment with action id
            findNavController().navigate(R.id.action_movieListFragment_to_addMovieFragment)
        }

    }

    override fun onMovieClick(moviePosition: Int) {
        // create an action to navigate to the DisplayMovieFragment
        // with the selected movie at moviePosition
        val actionMovieListFragmentToDisplayMovieFragment =
            MovieListFragmentDirections.actionMovieListFragmentToDisplayMovieFragment(
                Movies.list[moviePosition])
        // use the navigate method to perform the navigation action created above
        // we do not use the id of the action in this case
        findNavController().navigate(actionMovieListFragmentToDisplayMovieFragment)
    }

    override fun onCheckboxClick(moviePosition: Int) {
        val movie = Movies.list[moviePosition]
        // Toggle the status of the movie
        movie.status = if (movie.status == STATUS.UNWATCHED) {
            STATUS.WATCHED
        } else {
            STATUS.UNWATCHED
        }

        // Notify the adapter that the data has changed
        binding.list.adapter?.notifyDataSetChanged()

        // Introduce a delay of 1 second before sorting the list
        Handler(Looper.getMainLooper()).postDelayed({
            // Reorder the list so that unwatched movies appear on top
            Movies.list.sortByDescending { it.status }
            // Notify the adapter of the reordered list
            binding.list.adapter?.notifyDataSetChanged()
        }, 500) // Delay of 1 second (1000 milliseconds)
    }


    override fun onMovieLongClick(moviePosition: Int) {
        showDeleteDialog(moviePosition)
    }

    // show a dialog window to delete a movie at given position
    private fun showDeleteDialog(moviePosition: Int) {
        // get the movie to delete from the list
        val movieToDelete = Movies.list[moviePosition]
        // Define a dialog window with the AlertDialog.Builder class
        val dialogBuilder = android.app.AlertDialog.Builder(requireContext())
        // set the title  for the dialog
        dialogBuilder.setTitle("Delete Movie?")
        // set the message for the dialog
        dialogBuilder.setMessage(movieToDelete.title)
            // set the positive button for the dialog
            .setPositiveButton("Confirm") { _, _ ->
                deleteDialogPositiveClick(moviePosition)
            }
            // set the negative button for the dialog }
            .setNegativeButton("Cancel") { dialog, _ ->
                // if the user cancels the deletion, dismiss the dialog
                dialog.dismiss()
                deleteDialogNegativeClick(moviePosition)
            }
        // create the dialog
        val alert = dialogBuilder.create()
        // show the dialog
        alert.show()
    }

    private fun deleteDialogPositiveClick(moviePosition: Int) {
        // Check if the movie position is within the list bounds
        if (moviePosition >= 0 && moviePosition < Movies.list.size) {
            // Remove the movie from the list
            Movies.list.removeAt(moviePosition)
            // Notify the adapter that the data has changed
            binding.list.adapter?.notifyItemRemoved(moviePosition)
            // Show a snackbar message to confirm the deletion
            Snackbar.make(binding.root, "Movie deleted", Snackbar.LENGTH_SHORT).show()
        } else {
            // Log a warning if the movie position is invalid
            Log.w("MovieListFragment", "Invalid movie position: $moviePosition")
        }
    }

    private fun deleteDialogNegativeClick(moviePosition: Int) {
        // show a snackbar message to confirm the cancellation.
        // The action specified for the snackbar allows to add "REDO" button to }
        // show the dialog again
        Snackbar.make(binding.root, "Deletion cancelled", Snackbar.LENGTH_SHORT)
            .setAction("Redo") {
                // if the user wants to redo the deletion, call the showDeleteDialog method again
                showDeleteDialog(moviePosition)
            }.show()
    }
}
