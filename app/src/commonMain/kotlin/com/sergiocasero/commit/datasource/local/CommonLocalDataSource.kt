package com.sergiocasero.commit.datasource.local

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.result.Success

class CommonLocalDataSource : LocalDataSource {
    
    override suspend fun saveFavSlot(slot: Slot): Either<Error, Success> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getFavSlots(): Either<Error, List<Slot>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun remoteFavSlot(slot: Slot): Either<Error, Success> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun isSlotFav(slot: Slot): Either<Error, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun clear(): Either<Error, Success> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
