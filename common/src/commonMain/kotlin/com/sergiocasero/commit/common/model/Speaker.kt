package com.sergiocasero.commit.common.model

data class Speaker(
    val avatar: String,
    val description: String?,
    val id: Long,
    val isOrganizer: Boolean,
    val name: String,
    val rating: Rating?,
    val twitterAccount: String?,
    val uuid: String?
)
