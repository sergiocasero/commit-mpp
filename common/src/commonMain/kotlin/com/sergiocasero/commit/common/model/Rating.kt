package com.sergiocasero.commit.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val entriesCount: Int,
    val ratingAverage: Double
)
