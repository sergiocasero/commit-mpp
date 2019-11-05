package com.sergiocasero.commit.common.model

data class ContentsItem(
    val id: Long?,
    val type: String,
    val title: String?,
    val description: String?,
    val creationDate: Long?
)

data class Contents(
    val id: Long?,
    val type: String,
    val title: String?,
    val description: String?,
    val creationDate: Long?,
    val speakers: List<Speaker>
)
