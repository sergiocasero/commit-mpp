package com.sergiocasero.commit.common

data class Track(
    val id: Long,
    val name: String,
    val slots: List<Slot>
)