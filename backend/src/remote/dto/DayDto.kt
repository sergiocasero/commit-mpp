package com.sergiocasero.remote.dto

data class DayDto(
    val id: Long,
    val name: String,
    val tracks: List<TrackDto>
)
