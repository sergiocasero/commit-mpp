package com.sergiocasero.commit.di

import android.content.Context
import com.sergiocasero.commit.datasource.local.LocalDataSource
import com.sergiocasero.commit.datasource.remote.CommonRemoteDataSource
import com.sergiocasero.commit.datasource.remote.RemoteDataSource
import com.sergiocasero.commit.error.AndroidErrorHandler
import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor
import com.sergiocasero.commit.navigator.Navigator
import com.sergiocasero.commit.repository.ClientRepository
import com.sergiocasero.commit.repository.CommonClientRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

const val ACTIVITY_MODULE = "activityModule"

fun appModule(context: Context) = Kodein.Module("appModule") {
    bind<Context>() with singleton { context }
    bind<Executor>() with singleton { Executor() }
    bind<ErrorHandler>() with singleton { AndroidErrorHandler(context = context) }
    bind<Navigator>() with singleton { Navigator(context = context) }
}

val domainModule = Kodein.Module("domainModule") {
    bind<ClientRepository>() with singleton { CommonClientRepository(remote = instance(), local = instance()) }
}

val dataModule = Kodein.Module("dataModule") {
    bind<LocalDataSource>() with singleton { LocalDataSource(context = instance()) }
    bind<RemoteDataSource>() with singleton { CommonRemoteDataSource() }
}
