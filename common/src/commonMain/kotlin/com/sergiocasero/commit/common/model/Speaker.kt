package com.sergiocasero.commit.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Speaker(
    val avatar: String,
    val description: String? = null,
    val id: Long,
    val isOrganizer: Boolean,
    val name: String,
    val rating: Rating? = null,
    val twitterAccount: String? = null,
    val uuid: String? = null
)
