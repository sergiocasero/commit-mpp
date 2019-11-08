package com.sergiocasero.commit.datasource.local

import com.github.florent37.preferences.Preferences
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.model.SlotsResponse
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.result.Success
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

class CommonLocalDataSource: LocalDataSource {

    companion object {
        private const val SLOTS_KEY = "SLOTS_KEY"
    }

    private val preferences = Preferences()

    @UnstableDefault
    override fun saveFavSlot(slot: Slot): Either<Error, Success> = execute {
        val slotsResponse = getFavSlotsResponse()
        val slots = slotsResponse.slots.toMutableList()

        slots.add(slot)
        preferences.setString(SLOTS_KEY, Json.stringify(SlotsResponse.serializer(), SlotsResponse(slots)))

        Success
    }

    @UnstableDefault
    override fun getFavSlots(): Either<Error, List<Slot>> = execute {
        val slotsResponse = getFavSlotsResponse()
        slotsResponse.slots
    }

    @UnstableDefault
    override fun remoteFavSlot(slot: Slot): Either<Error, Success> = execute {
        val slotsResponse = getFavSlotsResponse()
        val slots = slotsResponse.slots.toMutableList()

        slots.remove(slot)
        preferences.setString(SLOTS_KEY, Json.stringify(SlotsResponse.serializer(), SlotsResponse(slots)))

        Success
    }

    @UnstableDefault
    override fun isSlotFav(slot: Slot): Either<Error, Boolean> = execute {
        val slotsResponse = getFavSlotsResponse()
        slotsResponse.slots.contains(slot)
    }

    private fun getFavSlotsResponse(): SlotsResponse = when(preferences.hasKey(SLOTS_KEY)) {
        true -> Json.parse(SlotsResponse.serializer(), preferences.getString(SLOTS_KEY) ?: "")
        false -> {
            val slotsResponse = SlotsResponse(listOf())
            preferences.setString(SLOTS_KEY, Json.stringify(SlotsResponse.serializer(), slotsResponse))
            slotsResponse
        }
    }

    override fun clear(): Either<Error, Success> = execute {
        preferences.clear()
        Success
    }

    private fun <T> execute(f: () -> T): Either<Error, T> =
        try {
            Either.Right(f())
        } catch (e: Exception) {
            Either.Left(Error.Default)
        }

}
