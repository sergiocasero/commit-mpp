package com.sergiocasero.commit.mapper

import com.sergiocasero.commit.common.model.DayItem
import com.sergiocasero.commit.models.DayView

fun DayItem.toView(pos: Int): DayView = DayView(
    pos = pos,
    title = name,
    selected = default
)