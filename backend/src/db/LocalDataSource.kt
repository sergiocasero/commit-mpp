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

    suspend fun getDays(): Either<Error, DaysResponse>
    suspend fun getTracks(): Either<Error, TracksResponse>

    suspend fun getDay(dayId: Long): Either<Error, Day>
    suspend fun getTrack(trackId: Long): Either<Error, Track>
    suspend fun getSlots(): Either<Error, SlotsResponse>
    suspend fun getSlot(slotId: Long): Either<Error, Slot>
}

class H2LocalDataSource : LocalDataSource {

    private fun getSlotSpeakers(contentId: Long): List<Speaker> = transaction {
        val ids = SlotSpeakerVo.select { SlotSpeakerVo.slotId eq contentId }.toList()

        ids.map { SpeakerVo.select { SpeakerVo.id eq it[SlotSpeakerVo.speakerId] }.first().toSpeaker() }
            .sortedBy { it.name }
    }

    override suspend fun getSlots(): Either<Error, SlotsResponse> = execute {
        SlotsResponse(
            transaction {
                SlotVo.selectAll().toList().map {
                    val speakers = getSlotSpeakers(it[SlotVo.id])
                    val contents =
                        ContentsVo.select { ContentsVo.slotId eq it[SlotVo.id] }.firstOrNull()?.toContents(speakers)
                    it.toSlot(contents)
                }
            }.sortedBy { it.start })
    }

    override suspend fun getSlot(slotId: Long): Either<Error, Slot> = execute {
        transaction {
            val slotVo = SlotVo.select { SlotVo.id eq slotId }.first()
            val speakers = getSlotSpeakers(slotId)
            val contents =
                ContentsVo.select { ContentsVo.slotId eq slotVo[SlotVo.id] }.firstOrNull()?.toContents(speakers)
            slotVo.toSlot(contents)
        }
    }

    override suspend fun getTrack(trackId: Long): Either<Error, Track> = execute {
        val slots = transaction {
            SlotVo.select { SlotVo.trackId eq trackId }.toList().map {
                val speakers = getSlotSpeakers(it[SlotVo.id])
                val contents =
                    ContentsVo.select { ContentsVo.slotId eq it[SlotVo.id] }.firstOrNull()?.toContents(speakers)
                it.toSlot(contents)
            }.sortedBy { it.start }
        }
        val trackItem = transaction { TrackVo.select { TrackVo.id eq trackId }.first().toTrack() }

        Track(slots = slots, id = trackItem.id, name = trackItem.name)
    }

    override suspend fun getTracks(): Either<Error, TracksResponse> = execute {
        transaction { TracksResponse(TrackVo.selectAll().toList().map { it.toTrack() }.sortedBy { it.name }) }
    }

    override suspend fun getDay(dayId: Long): Either<Error, Day> = execute {
        val tracks =
            transaction { TrackVo.select { TrackVo.dayId eq dayId }.toList().map { it.toTrack() } }.sortedBy { it.name }
        val dayItem = transaction { DayVo.select { DayVo.id eq dayId }.first().toDay() }

        Day(id = dayItem.id, name = dayItem.name, tracks = tracks)
    }

    override suspend fun getDays(): Either<Error, DaysResponse> = execute {
        transaction { DaysResponse(DayVo.selectAll().toList().map { it.toDay() }.sortedBy { it.name }) }
    }

    override suspend fun saveData(data: CommitResponse) {
        transaction {
            SchemaUtils.create(DayVo, TrackVo, SlotVo, ContentsVo, SpeakerVo, SlotSpeakerVo)
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
                                it[creationDate] = DateTime(slotContent.creationDate ?: 0)
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

                                SlotSpeakerVo.insert {
                                    it[slotId] = slot.id
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

    private fun ResultRow.toContents(speakers: List<Speaker>) = Contents(
        id = this[ContentsVo.id],
        type = this[ContentsVo.type],
        description = this[ContentsVo.description],
        creationDate = this[ContentsVo.creationDate].millis,
        title = this[ContentsVo.title],
        speakers = speakers
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

    private fun ResultRow.toSpeaker() = Speaker(
        id = this[SpeakerVo.id],
        name = this[SpeakerVo.name],
        description = this[SpeakerVo.description],
        avatar = this[SpeakerVo.avatar],
        isOrganizer = this[SpeakerVo.isOrganizer] == 1,
        rating = Rating(ratingAverage = this[SpeakerVo.ratingAverage], entriesCount = this[SpeakerVo.entriesCount]),
        twitterAccount = this[SpeakerVo.twitterAccount],
        uuid = this[SpeakerVo.uuid]
    )
}
