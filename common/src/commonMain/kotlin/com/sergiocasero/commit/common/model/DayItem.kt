package com.sergiocasero.commit.common.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class DaysResponse(
    val items: List<DayItem>
)

@Serializable
data class DayItem(
    val id: Long,
    val name: String,
    val default: Boolean
)

@Serializable
data class Day(
    val id: Long,
    val name: String,
    val tracks: List<TrackItem>
)
