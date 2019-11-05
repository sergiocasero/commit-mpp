package com.sergiocasero.commit.common

import com.sergiocasero.remote.dto.RatingDto

data class SpeakerDto(
    val avatar: String,
    val description: String?,
    val id: Long,
    val isOrganizer: Boolean,
    val name: String,
    val rating: RatingDto?,
    val twitterAccount: String?,
    val uuid: String?
)
