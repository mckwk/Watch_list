package com.example.mytodo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
// this is the enum class that will be used to store the importance of the movies
enum class STATUS {
    WATCHED, UNWATCHED
}

//This is the data class that will be used to store the movies
@Parcelize //@Parcelize is an annotation that allows you to make your class Parcelable
data class Movie(
    val id: String,
    val title: String,
    val description: String,
    var status: STATUS = STATUS.UNWATCHED
) : Parcelable  // Parcelable is an interface that allows you to pass data between activities
                // and fragments