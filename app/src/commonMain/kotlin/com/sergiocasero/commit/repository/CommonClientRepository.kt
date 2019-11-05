package com.sergiocasero.commit.repository

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error

class CommonClientRepository: ClientRepository {
    override suspend fun getSlot(slotId: Long): Either<Error, Slot> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
