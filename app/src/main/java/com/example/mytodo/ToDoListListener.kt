package com.example.mytodo

interface ToDoListListener {
    fun onMovieClick(taskPosition: Int)
    fun onMovieLongClick(taskPosition: Int)

    fun onCheckboxClick(taskPosition: Int)
}