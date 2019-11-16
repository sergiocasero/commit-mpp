package com.sergiocasero.commit.common.model

import kotlinx.serialization.Serializable

@Serializable
data class TrackItem(
    val id: Long,
    val name: String
)

@Serializable
data class Track(
    val id: Long,
    val name: String,
    val slots: List<Slot>
)
