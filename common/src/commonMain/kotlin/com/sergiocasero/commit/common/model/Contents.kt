package com.sergiocasero.commit.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Contents(
    val id: Long?,
    val type: String,
    val title: String?,
    val description: String?,
    val creationDate: Long?,
    val speakers: List<Speaker>
)
