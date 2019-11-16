package com.sergiocasero.commit.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Slot(
    val contents: Contents? = null,
    val end: String,
    val id: Long,
    val start: String,
    val trackId: Long,
    val userId: Int,
    val state: String? = null
)
