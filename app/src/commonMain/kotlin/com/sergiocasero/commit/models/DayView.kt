package com.sergiocasero.commit.models

data class DayView(
    val pos: Int,
    val title: String,
    var selected: Boolean = false
)