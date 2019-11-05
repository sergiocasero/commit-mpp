package com.sergiocasero.remote.dto

data class TrackDto(
    val id: Long,
    val name: String,
    val slots: List<SlotDto>
)
