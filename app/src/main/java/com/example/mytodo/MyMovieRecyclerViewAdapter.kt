package com.example.mytodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.data.IMPORTANCE
import com.example.mytodo.data.Movie
import com.example.mytodo.databinding.FragmentMovieItemBinding

// Adapter is responsible for managing the display of the list –binding data with the views
class MyMovieRecyclerViewAdapter(
    private val values: List<Movie>,
    private val eventListener: ToDoListListener
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
        // The method binds (connects) the data to the views in the view holder visible to the user

        val movie = values[position] // get the movie at the current position

        // select the drawable resource for image view based on importance of the movie
        val importanceImage = when(movie.importance){
            IMPORTANCE.LOW -> R.drawable.circle_drawable_green
            IMPORTANCE.NORMAL -> R.drawable.circle_drawable_orange
            IMPORTANCE.HIGH -> R.drawable.circle_drawable_red
        }

        // set the image view and text view with movie data
        holder.imgView.setImageResource(importanceImage)
        holder.contentView.text = movie.title


        // set the click and long click listeners for the view holder
        holder.itemContainer.setOnClickListener {
            eventListener.onMovieClick(position)
        }
        holder.itemContainer.setOnLongClickListener {
            eventListener.onMovieLongClick(position)
            return@setOnLongClickListener true // consume the long click event
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }

}