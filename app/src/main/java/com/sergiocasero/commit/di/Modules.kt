package com.sergiocasero.commit.di

import android.content.Context
import com.sergiocasero.commit.common.datasource.local.CommonLocalDataSource
import com.sergiocasero.commit.common.datasource.local.LocalDataSource
import com.sergiocasero.commit.common.datasource.remote.CommonRemoteDataSource
import com.sergiocasero.commit.common.datasource.remote.RemoteDataSource
import com.sergiocasero.commit.common.error.AndroidErrorHandler
import com.sergiocasero.commit.common.error.ErrorHandler
import com.sergiocasero.commit.common.executor.AndroidExecutor
import com.sergiocasero.commit.common.navigator.AndroidNavigator
import com.sergiocasero.commit.common.repository.ClientRepository
import com.sergiocasero.commit.common.repository.CommonClientRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

const val ACTIVITY_MODULE = "activityModule"

fun appModule(context: Context) = Kodein.Module("appModule") {
    bind<Context>() with singleton { context }
    bind<AndroidExecutor>() with singleton { AndroidExecutor() }
    bind<ErrorHandler>() with singleton { AndroidErrorHandler(context = context) }
    bind<AndroidNavigator>() with singleton { AndroidNavigator(context = context) }
}

val domainModule = Kodein.Module("domainModule") {
    bind<ClientRepository>() with singleton { CommonClientRepository(remote = instance(), local = instance()) }
}

val dataModule = Kodein.Module("dataModule") {
    bind<LocalDataSource>() with singleton { CommonLocalDataSource() }
    bind<RemoteDataSource>() with singleton { CommonRemoteDataSource() }
}
