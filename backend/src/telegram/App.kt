package com.sergiocasero.telegram

import com.sergiocasero.db.H2LocalDataSource
import com.sergiocasero.remote.KtorRemoteDataSource
import com.sergiocasero.repository.CommitBackendRepository
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.command
import org.jetbrains.exposed.sql.Database

class App {
    private val repository = CommitBackendRepository(local = H2LocalDataSource(), remote = KtorRemoteDataSource())

    private val bot = Bot.Builder().apply {
        token = ""
        updater.dispatcher.apply {
            val helloCommand = HelloWorldCommand()
            val daysCommand = DaysCommand(repository)
            val dayCommand = DayCommand(repository)
            val trackCommand = TrackCommand(repository)
            val slotCommand = SlotCommand(repository)
            command(helloCommand.commandName, helloCommand.commandAction)
            command(daysCommand.commandName, daysCommand.commandAction)
            command(dayCommand.commandName, dayCommand.commandAction)
            command(trackCommand.commandName, trackCommand.commandAction)
            command(slotCommand.commandName, slotCommand.commandAction)
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