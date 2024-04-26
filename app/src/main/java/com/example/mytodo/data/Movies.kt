package com.example.mytodo.data

object Movies {
    val list: MutableList<Movie> = ArrayList()

    private val COUNT = 1

    init {
        addMovie(Movie("1", "Pride and Prejudice", makeDetails(1)))
        addMovie(Movie("2", "Back to the Future", makeDetails(2)))
        addMovie(Movie("3", "Shrek", makeDetails(3)))

    }
    fun addMovie(movie: Movie) {
        list.add(movie)
    }

    private fun createPlaceholderMovie(position: Int): Movie {
        return Movie(position.toString(), "Movie $position", makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Movie: ").append(position)
        for (i in 0..position) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    fun updateMovie(oldMovie: Movie?, newMovie: Movie) {
        //perform the update operation only if the old movie is not null
        oldMovie?.let { old ->
            //find the index of the old movie in the list
            // indexOf will be -1 if the old movie is not in the list
            val indexOfOld = list.indexOf(old)
            if (indexOfOld != -1) { // check if the old movie is in the list //replace the old movie with the new movie
                list[indexOfOld] = newMovie
            }
        }
    }
}