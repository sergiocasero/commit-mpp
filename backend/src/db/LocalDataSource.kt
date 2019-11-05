package com.sergiocasero.db

import com.sergiocasero.commit.common.CommitResponse
import com.sergiocasero.commit.common.model.*
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

interface LocalDataSource {
    suspend fun saveData(data: CommitResponse)

    suspend fun getDays(): Either<Error, ListResponse<DayItem>>
    suspend fun getTracks(): Either<Error, ListResponse<TrackItem>>

    suspend fun getDay(dayId: Long): Either<Error, Day>
    suspend fun getTrack(trackId: Long): Either<Error, Track>
}

class H2LocalDataSource : LocalDataSource {
    override suspend fun getTrack(trackId: Long): Either<Error, Track> = execute {
        val slots = transaction {
            SlotVo.select { SlotVo.trackId eq trackId }.toList().map {
                val contents = ContentsVo.select { ContentsVo.slotId eq it[SlotVo.id] }.firstOrNull()?.toContents()
                it.toSlot(contents)
            }
        }
        val trackItem = transaction { TrackVo.select { TrackVo.id eq trackId }.first().toTrack() }

        Track(slots = slots, id = trackItem.id, name = trackItem.name)
    }

    override suspend fun getTracks(): Either<Error, ListResponse<TrackItem>> = execute {
        transaction { ListResponse(TrackVo.selectAll().toList().map { it.toTrack() }) }
    }

    override suspend fun getDay(dayId: Long): Either<Error, Day> = execute {
        val tracks = transaction { TrackVo.select { TrackVo.dayId eq dayId }.toList().map { it.toTrack() } }
        val dayItem = transaction { DayVo.select { DayVo.id eq dayId }.first().toDay() }

        Day(id = dayItem.id, name = dayItem.name, tracks = tracks)
    }

    override suspend fun getDays(): Either<Error, ListResponse<DayItem>> = execute {
        transaction { ListResponse(DayVo.selectAll().toList().map { it.toDay() }) }
    }

    override suspend fun saveData(data: CommitResponse) {
        transaction {
            SchemaUtils.create(DayVo, TrackVo, SlotVo, ContentsVo, SpeakerVo, ContentsSpeakerVo)
            data.days.forEach { day ->
                DayVo.insert {
                    it[id] = day.id
                    it[name] = day.name
                }

                day.tracks.forEach { track ->
                    TrackVo.insert {
                        it[id] = track.id
                        it[dayId] = day.id
                        it[name] = track.name
                    }

                    track.slots.forEach { slot ->
                        SlotVo.insert {
                            it[id] = slot.id
                            it[start] = slot.start
                            it[end] = slot.end
                            it[trackId] = track.id
                            it[userId] = slot.userId
                        }

                        slot.contents?.let { slotContent ->
                            ContentsVo.insert {
                                it[id] = slotContent.id ?: 0
                                it[type] = slotContent.type
                                it[title] = slotContent.title
                                it[description] = slotContent.description
                                it[creationDate] = DateTime(slotContent.creationDate)
                                it[slotId] = slot.id
                            }

                            slotContent.authors?.forEach { speaker ->
                                SpeakerVo.insert {
                                    it[id] = speaker.id
                                    it[avatar] = speaker.avatar
                                    it[uuid] = speaker.uuid ?: ""
                                    it[name] = speaker.name
                                    it[description] = speaker.description
                                    it[ratingAverage] = speaker.rating?.ratingAverage ?: 0.0
                                    it[entriesCount] = speaker.rating?.entriesCount ?: 0
                                    it[isOrganizer] = if (speaker.isOrganizer) 1 else 0
                                    it[twitterAccount] = speaker.twitterAccount
                                }

                                ContentsSpeakerVo.insert {
                                    it[contentsId] = slotContent.id ?: 0
                                    it[speakerId] = speaker.id
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun <R> execute(f: () -> R): Either<Error, R> {
        return try {
            Either.Right(f())
        } catch (exception: Exception) {
            Either.Left(
                when (exception) {
                    is NoSuchElementException -> Error.NotFound
                    else -> Error.Default
                }
            )
        }
    }

    private fun ResultRow.toDay() = DayItem(
        id = this[DayVo.id],
        name = this[DayVo.name]
    )

    private fun ResultRow.toTrack() = TrackItem(
        id = this[TrackVo.id],
        name = this[TrackVo.name]
    )

    private fun ResultRow.toContents() = Contents(
        id = this[ContentsVo.id],
        type = this[ContentsVo.type],
        description = this[ContentsVo.description],
        creationDate = this[ContentsVo.creationDate].millis,
        title = this[ContentsVo.title]
    )

    private fun ResultRow.toSlot(contents: Contents?) = Slot(
        id = this[SlotVo.id],
        contents = contents,
        end = this[SlotVo.end],
        start = this[SlotVo.start],
        state = this[SlotVo.state],
        trackId = this[SlotVo.trackId],
        userId = this[SlotVo.userId]
    )
}
