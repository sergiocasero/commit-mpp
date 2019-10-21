package com.sergiocasero.commit.di

import android.content.Context
import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

const val ACTIVITY_MODULE = "activityModule"

fun appModule(context: Context) = Kodein.Module("appModule") {
    bind<Context>() with singleton { context }
    bind<Executor>() with singleton { Executor() }
    bind<ErrorHandler>() with singleton { ErrorHandler(context = context) }
}

val domainModule = Kodein.Module("domainModule") {
    // bind<Repository>() with singleton { AndroidRepository(localDataSource = instance()) }
}

val dataModule = Kodein.Module("dataModule") {
    // bind<LocalDataSource>() with singleton { AndroidLocalDataSource(context = instance()) }
}
