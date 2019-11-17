package com.sergiocasero.commit.common.mapper

import com.sergiocasero.commit.common.model.DayItem
import com.sergiocasero.commit.common.models.DayView

fun DayItem.toView(pos: Int): DayView = DayView(
    pos = pos,
    title = name
)
