package com.sergiocasero.commit.common

data class CommitResponse(
    val days: List<Day>,
    val feedbackEnabled: Boolean,
    val id: Long,
    val published: Boolean
)