package com.sergiocasero.commit.common.model

import kotlinx.serialization.Serializable

@Serializable
data class ListResponse<T>(val items: List<T>)
