package com.sergiocasero.desktop

import com.sergiocasero.commit.common.datasource.local.LocalDataSource
import com.sergiocasero.commit.common.error.ErrorHandler
import com.sergiocasero.commit.common.executor.Executor
import com.sergiocasero.commit.common.navigator.Navigator
import com.sergiocasero.commit.common.result.Error
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.launch

fun main() {
    launch<Commit>()
}

class DesktopNavigator: Navigator {
    override fun openSpeakerTwitter(twitterUser: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToSlotDetail(slotId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class DesktopErrorHandler: ErrorHandler {
    override fun convert(error: Error): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class DesktopExecutor(override val main: CoroutineDispatcher = Dispatchers.JavaFx) : Executor

class DesktopLocalDataSource: LocalDataSource