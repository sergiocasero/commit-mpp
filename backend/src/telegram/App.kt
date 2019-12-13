package com.sergiocasero.telegram

import com.sergiocasero.db.H2LocalDataSource
import com.sergiocasero.remote.KtorRemoteDataSource
import com.sergiocasero.repository.CommitBackendRepository
import me.ivmg.telegram.Bot
import org.jetbrains.exposed.sql.Database

class App {
    private val repository = CommitBackendRepository(local = H2LocalDataSource(), remote = KtorRemoteDataSource())

    private val bot = Bot.Builder().apply {
        token = ""
        updater.dispatcher.apply {
            command(HelloWorldCommand())
            command(DaysCommand(repository))
            command(DayCommand(repository))
            command(TrackCommand(repository))
            command(SlotCommand(repository))
        }
    }.build()

    fun run() {
        Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        bot.startPolling()
    }
}

fun main(args: Array<String>) {
    App().run()
}