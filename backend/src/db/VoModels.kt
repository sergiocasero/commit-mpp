package com.sergiocasero.db

import org.jetbrains.exposed.sql.Table

object DayVo : Table("day") {
    val id = long("id").primaryKey()
    val name = varchar("name", 50)
}

object TrackVo : Table("track") {
    val id = long("id").primaryKey()
    val dayId = long("day_id")
    val name = varchar("name", 50)
}

object SlotVo : Table("slot") {
    val id = long("id").primaryKey()
    val start = varchar("start", 5)
    val end = varchar("end", 5)
    val userId = integer("user_id")
    val trackId = long("track_id")
    val state = varchar("state", 40).nullable()
}

object ContentsVo : Table("contents") {
    val id = long("id")
    val slotId = long("slotId")
    val type = varchar("type", 50)
    val title = varchar("title", 255).nullable()
    val description = text("description").nullable()
    val creationDate = datetime("creationDate")
}

object SpeakerVo : Table("speaker") {
    val id = long("id")
    val uuid = varchar("uuid", 50)
    val name = varchar("name", 150)
    val avatar = text("avatar")
    val description = text("description").nullable()
    val ratingAverage = double("ratingAverage")
    val entriesCount = integer("entriesCount")
    val isOrganizer = integer("isOrganizer")
    val twitterAccount = varchar("twitterAccount", 60).nullable()
}

object SlotSpeakerVo : Table() {
    val slotId = long("slotId")
    val speakerId = long("speakerId")
}
