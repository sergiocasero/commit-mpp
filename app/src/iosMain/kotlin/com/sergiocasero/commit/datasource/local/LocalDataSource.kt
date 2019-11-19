package com.sergiocasero.commit.datasource.local

import com.sergiocasero.commit.common.model.Slot
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
            val savedSlotsString = nSUserDefaults.stringForKey(SAVED_SLOT_KEY) ?: "[]"
            val slots = Json.parse<MutableList<Slot>>(savedSlotsString)
            slots.add(slot)
            val json = Json.stringify(slots)
            nSUserDefaults.setObject(json, SAVED_SLOT_KEY)
            Either.Right(Success)
        } catch (e: Exception) {
            Either.Left(Error.Default)
        }
    }

    @ImplicitReflectionSerializer
    actual fun getFavSlots(): Either<Error, List<Slot>> {
        return try {
            val savedSlotsString = nSUserDefaults.stringForKey(SAVED_SLOT_KEY) ?: "[]"
            val slots = Json.parse<MutableList<Slot>>(savedSlotsString)
            Either.Right(slots)
        } catch (e: Exception) {
            Either.Left(Error.Default)
        }
    }

    actual fun remoteFavSlot(slot: Slot): Either<Error, Success> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @ImplicitReflectionSerializer
    actual fun isSlotFav(slot: Slot): Either<Error, Boolean> {
        return try {
            val savedSlotsString = nSUserDefaults.stringForKey(SAVED_SLOT_KEY) ?: "[]"
            val slots = Json.parse<MutableList<Slot>>(savedSlotsString)
            Either.Right(slots.contains(slot))
        } catch (e: Exception) {
            Either.Left(Error.Default)
        }
    }

    actual fun clear(): Either<Error, Success> {
        return try {
            nSUserDefaults.removeObjectForKey(SAVED_SLOT_KEY)
            Either.Right(Success)
        } catch (e: Exception) {
            Either.Left(Error.Default)
        }
    }

}