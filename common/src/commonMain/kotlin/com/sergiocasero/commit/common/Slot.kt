package com.sergiocasero.commit.common

data class Slot(
    val contents: Contents,
    val end: String,
    val id: Int,
    val start: String,
    val trackId: Long,
    val userId: Int
)