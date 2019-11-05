package com.sergiocasero.commit.common.model

data class DayItem(
    val id: Long,
    val name: String
)

data class Day(
    val id: Long,
    val name: String,
    val tracks: List<TrackItem>
)
