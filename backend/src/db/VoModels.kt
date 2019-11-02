package com.sergiocasero.db

import org.jetbrains.exposed.sql.Table

object DayVo: Table("day") {
    val id = integer("id").primaryKey()
    val name = varchar("name", 50)
}

object TrackVo: Table("track") {
    val id = integer("id").primaryKey()
    val dayId = integer("day_id")
    val name = varchar("name", 50)
}

object SlotVo: Table("slot") {
    val id = integer("id").primaryKey()
    val start = varchar("start", 5)
    val end = varchar("end", 5)
    val userId = integer("user_id")
    val trackId = integer("track_id")
}