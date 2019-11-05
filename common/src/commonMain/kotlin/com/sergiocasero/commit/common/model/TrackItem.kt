package com.sergiocasero.commit.common.model

data class TrackItem(
    val id: Long,
    val name: String
)

data class Track(
    val id: Long,
    val name: String,
    val slots: List<Slot>
)
