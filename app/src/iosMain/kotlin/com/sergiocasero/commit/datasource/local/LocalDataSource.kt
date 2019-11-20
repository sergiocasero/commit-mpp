package com.sergiocasero.commit.datasource.local

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.model.SlotsResponse
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.result.Success
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import platform.Foundation.NSUserDefaults

actual class LocalDataSource {

    companion object {
        const val SAVED_SLOT_KEY = "SAVED_SLOT_KEY"
    }

    private val nSUserDefaults = NSUserDefaults()

    @ImplicitReflectionSerializer
    actual fun saveFavSlot(slot: Slot): Either<Error, Success> {
        return try {
            val savedSlotsString = nSUserDefaults.stringForKey(SAVED_SLOT_KEY)
            val slots = if (savedSlotsString != null) {
                val slotsResponse = Json.parse<SlotsResponse>(savedSlotsString)
                slotsResponse.slots.toMutableList()
            } else {
                mutableListOf()
            }
            slots.add(slot)
            val json = Json.stringify(SlotsResponse(slots))
            nSUserDefaults.setObject(json, SAVED_SLOT_KEY)
            Either.Right(Success)
        } catch (e: Exception) {
            print(e)
            Either.Left(Error.Default)
        }
    }

    @ImplicitReflectionSerializer
    actual fun getFavSlots(): Either<Error, List<Slot>> {
        return try {
            val savedSlotsString = nSUserDefaults.stringForKey(SAVED_SLOT_KEY)
            if (savedSlotsString != null) {
                val slotsResponse = Json.parse<SlotsResponse>(savedSlotsString)
                Either.Right(slotsResponse.slots)
            } else {
                Either.Right(listOf())
            }

        } catch (e: Exception) {
            print(e)
            Either.Left(Error.Default)
        }
    }

    @ImplicitReflectionSerializer
    actual fun removeFavSlot(slot: Slot): Either<Error, Success> {
        return try {
            val savedSlotsString = nSUserDefaults.stringForKey(SAVED_SLOT_KEY)
            val slots = if (savedSlotsString != null) {
                val slotsResponse = Json.parse<SlotsResponse>(savedSlotsString)
                slotsResponse.slots.toMutableList()
            } else {
                mutableListOf()
            }
            slots.remove(slot)
            val json = Json.stringify(SlotsResponse(slots))
            nSUserDefaults.setObject(json, SAVED_SLOT_KEY)
            Either.Right(Success)
        } catch (e: Exception) {
            print(e)
            Either.Left(Error.Default)
        }
    }

    @ImplicitReflectionSerializer
    actual fun isSlotFav(slot: Slot): Either<Error, Boolean> {
        return try {
            val savedSlotsString = nSUserDefaults.stringForKey(SAVED_SLOT_KEY)
            if (savedSlotsString != null) {
                val slotsResponse = Json.parse<SlotsResponse>(savedSlotsString)
                Either.Right(slotsResponse.slots.contains(slot))
            } else {
                Either.Right(false)
            }
        } catch (e: Exception) {
            print(e)
            Either.Left(Error.Default)
        }
    }

    actual fun clear(): Either<Error, Success> {
        return try {
            nSUserDefaults.removeObjectForKey(SAVED_SLOT_KEY)
            Either.Right(Success)
        } catch (e: Exception) {
            print(e)
            Either.Left(Error.Default)
        }
    }

}