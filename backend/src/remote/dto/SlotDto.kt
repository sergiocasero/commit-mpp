package com.sergiocasero.remote.dto

data class SlotDto(
    val contents: ContentsDto?,
    val end: String,
    val id: Long,
    val start: String,
    val trackId: Long,
    val userId: Int,
    val state: String?
)
