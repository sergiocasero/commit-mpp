package com.sergiocasero.remote.dto

import com.sergiocasero.commit.common.SpeakerDto

data class ContentsDto(
    val trackId: Long?,
    val id: Long?,
    val type: String,
    val title: String?,
    val description: String?,
    val creationDate: Long?,
    val authors: List<SpeakerDto>?,
    val tags: Map<String, List<String>>?
)
