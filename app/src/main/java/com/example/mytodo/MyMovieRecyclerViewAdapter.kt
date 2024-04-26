package com.example.mytodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.data.STATUS
import com.example.mytodo.data.Movie
import com.example.mytodo.databinding.FragmentMovieItemBinding

// Adapter is responsible for managing the display of the list –binding data with the views
class MyMovieRecyclerViewAdapter(
    private val values: List<Movie>,
    private val eventListener: ToDoListListener,
    private val statusDrawable: Int
):RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder>()
{
    // The ViewHolder class is a container for the views in the recycler view item
    class ViewHolder(binding: FragmentMovieItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val imgView: ImageView = binding.itemImg
        val contentView: TextView = binding.content
        val itemContainer: View = binding.root
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    // onCreateViewHolder creates the ViewHolder objects
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyMovieRecyclerViewAdapter.ViewHolder {
        // create the view holders for the recycler view items
        // no data is bound to the views yet
        return ViewHolder(
            FragmentMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MyMovieRecyclerViewAdapter.ViewHolder, position: Int) {
        val movie = values[position] // Get the movie at the current position

        // Select the drawable resource for image view based on the importance of the movie
        val statusImage = when (movie.status) {
            STATUS.UNWATCHED -> R.drawable.unchecked
            STATUS.WATCHED -> R.drawable.checked
        }

        // Set the image view and text view with movie data
        holder.imgView.setImageResource(statusImage)
        holder.contentView.text = movie.title

        // Set the click and long click listeners for the view holder
        holder.itemContainer.setOnClickListener {
            eventListener.onMovieClick(position)
        }
        holder.itemContainer.setOnLongClickListener {
            eventListener.onMovieLongClick(position)
            true // Consume the long click event
        }

        // Set click listener for the checkbox
        holder.imgView.setOnClickListener {
            eventListener.onCheckboxClick(position)
        }
    }


    override fun getItemCount(): Int {
        return values.size
    }

}