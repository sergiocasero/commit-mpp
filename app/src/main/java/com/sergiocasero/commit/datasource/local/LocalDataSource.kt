package com.sergiocasero.commit.datasource.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.result.Success

actual class LocalDataSource(context: Context) {

    companion object {
        const val SAVED_SLOTS_KEY = "SAVED_SLOTS_KEY"
    }

    private val gson = Gson()

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("commit_prefs", Context.MODE_PRIVATE);

    actual fun saveFavSlot(slot: Slot): Either<Error, Success> {
        return try {
            val slots: MutableList<Slot> = if (sharedPreferences.contains(SAVED_SLOTS_KEY)) {
                val type = object : TypeToken<MutableList<Slot>>() {}.type
                gson.fromJson(getString(SAVED_SLOTS_KEY), type)
            } else {
                mutableListOf()
            }
            slots.add(slot)
            setString(SAVED_SLOTS_KEY, gson.toJson(slots))
            Either.Right(Success)
        } catch (e: Exception) {
            Either.Left(Error.Default)
        }
    }

    actual fun getFavSlots(): Either<Error, List<Slot>> {
        return try {
            if (sharedPreferences.contains(SAVED_SLOTS_KEY)) {
                val type = object : TypeToken<List<Slot>>() {}.type
                val slots: List<Slot> = gson.fromJson(getString(SAVED_SLOTS_KEY), type)
                Either.Right(slots)
            } else {
                Either.Right(listOf())
            }
        } catch (e: Exception) {
            Either.Left(Error.Default)
        }
    }

    actual fun removeFavSlot(slot: Slot): Either<Error, Success> {
        return try {
            if (sharedPreferences.contains(SAVED_SLOTS_KEY)) {
                val type = object : TypeToken<MutableList<Slot>>() {}.type
                val slots: MutableList<Slot> = gson.fromJson(getString(SAVED_SLOTS_KEY), type)
                slots.remove(slot)
                setString(SAVED_SLOTS_KEY, gson.toJson(slots))
            }
            Either.Right(Success)
        } catch (e: Exception) {
            Either.Left(Error.Default)
        }
    }

    actual fun isSlotFav(slot: Slot): Either<Error, Boolean> {
        return try {
            if (sharedPreferences.contains(SAVED_SLOTS_KEY)) {
                val type = object : TypeToken<List<Slot>>() {}.type
                val slots: List<Slot> = gson.fromJson(getString(SAVED_SLOTS_KEY), type)
                Either.Right(slots.contains(slot))
            } else {
                Either.Right(false)
            }
        } catch (e: Exception) {
            Either.Left(Error.Default)
        }
    }

    actual fun clear(): Either<Error, Success> {
        return try {
            sharedPreferences.edit().remove(SAVED_SLOTS_KEY).apply()
            Either.Right(Success)
        } catch (e: Exception) {
            Either.Left(Error.Default)
        }
    }

    private fun getString(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    private fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

}