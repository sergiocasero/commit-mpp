package com.sergiocasero.telegram

import com.sergiocasero.repository.CommitBackendRepository
import kotlinx.coroutines.runBlocking
import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

typealias CommandAction = (Bot, Update, List<String>) -> Unit

abstract class BotCommand(
    val commandName: String = "default",
    val commandAction: CommandAction
)

class HelloWorldCommand : BotCommand("helloworld",
    object : CommandAction {
        override fun invoke(bot: Bot, update: Update, args: List<String>) {
            update.message?.let {
                bot.sendMessage(it.chat.id, "Hello World!!")
            }
        }
    })


class DaysCommand(repository: CommitBackendRepository) : BotCommand(
    "days",
    object : CommandAction {
        override fun invoke(bot: Bot, update: Update, args: List<String>) {
            update.message?.let { msg ->
                runBlocking {
                    repository.getDays().fold(
                        error = { bot.sendMessage(msg.chat.id, "An error has ocurred") },
                        success = { days ->
                            bot.sendMessage(
                                msg.chat.id,
                                days.items.joinToString(separator = "\n") { "${it.name}: ${it.id}" })
                        })
                }
            }
        }
    })


class DayCommand(repository: CommitBackendRepository) : BotCommand(
    "day",
    object : CommandAction {
        override fun invoke(bot: Bot, update: Update, args: List<String>) {
            update.message?.let { msg ->
                runBlocking {
                    repository.getDay(args.first().toLong()).fold(
                        error = { bot.sendMessage(msg.chat.id, "An error has ocurred") },
                        success = { day ->
                            bot.sendMessage(
                                msg.chat.id,
                                day.tracks.joinToString(separator = "\n") { "${it.name}: ${it.id}" })
                        })
                }
            }
        }
    })

class TrackCommand(repository: CommitBackendRepository) : BotCommand(
    "track",
    object : CommandAction {
        override fun invoke(bot: Bot, update: Update, args: List<String>) {
            update.message?.let { msg ->
                runBlocking {
                    repository.getTrack(args.first().toLong()).fold(
                        error = { bot.sendMessage(msg.chat.id, "An error has ocurred") },
                        success = { track ->
                            bot.sendMessage(
                                msg.chat.id,
                                track.slots.joinToString(separator = "\n") { "${it.contents?.title}: ${it.id}" })
                        })
                }
            }
        }
    })


class SlotCommand(repository: CommitBackendRepository) : BotCommand(
    "slot",
    object : CommandAction {
        override fun invoke(bot: Bot, update: Update, args: List<String>) {
            update.message?.let { msg ->
                runBlocking {
                    repository.getSlot(args.first().toLong()).fold(
                        error = { bot.sendMessage(msg.chat.id, "An error has ocurred") },
                        success = { slot ->
                            bot.sendMessage(
                                msg.chat.id,
                                "${slot.contents?.title}\n" +
                                        "${slot.start} - ${slot.end}\n" +
                                        "${slot.contents?.description}"
                            )
                        })
                }
            }
        }
    })