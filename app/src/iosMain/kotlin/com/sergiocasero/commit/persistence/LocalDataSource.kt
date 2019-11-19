package com.sergiocasero.commit.persistence

import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver

actual fun createDb(): CommitDB {
    val driver = NativeSqliteDriver(CommitDB.Schema, "slots.db")
    return CommitDB(driver)
}