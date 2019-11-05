package com.sergiocasero.commit.common.model

import kotlinx.serialization.Serializable

@Serializable
data class DayItem(
    val id: Long,
    val name: String
)

@Serializable
data class Day(
    val id: Long,
    val name: String,
    val tracks: List<TrackItem>
)
