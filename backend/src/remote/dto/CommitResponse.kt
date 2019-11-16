package com.sergiocasero.commit.common

import com.sergiocasero.remote.dto.DayDto

data class CommitResponse(
    val days: List<DayDto>,
    val feedbackEnabled: Boolean,
    val id: Long,
    val published: Boolean
)
