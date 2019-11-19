package com.sergiocasero.commit.datasource.local

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.result.Success

actual class LocalDataSource {
    actual fun saveFavSlot(slot: Slot): Either<Error, Success> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun getFavSlots(): Either<Error, List<Slot>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun remoteFavSlot(slot: Slot): Either<Error, Success> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun isSlotFav(slot: Slot): Either<Error, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun clear(): Either<Error, Success> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}