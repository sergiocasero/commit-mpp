package com.sergiocasero.commit.persistence

import com.squareup.sqldelight.android.AndroidSqliteDriver

actual fun createDb(): CommitDB {
    val driver = AndroidSqliteDriver(CommitDB.Schema, appContext, "slots.db")
    return CommitDB(driver)
}