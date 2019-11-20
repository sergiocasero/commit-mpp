package com.sergiocasero.commit.common.model

import kotlinx.serialization.Serializable

@Serializable
data class SlotsResponse(val slots: List<Slot>)
