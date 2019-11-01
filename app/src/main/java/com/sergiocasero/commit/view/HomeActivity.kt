package com.sergiocasero.commit.view

import android.view.View
import com.sergiocasero.commit.R
import com.sergiocasero.commit.di.ACTIVITY_MODULE
import com.sergiocasero.commit.presenter.HomePresenter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class HomeActivity : RootActivity<HomePresenter.View>(), HomePresenter.View {

    override val progress: View by lazy { TODO() }

    override val presenter: HomePresenter by instance()

    override val layoutResourceId: Int = R.layout.activity_home


    override val activityModule: Kodein.Module = Kodein.Module(ACTIVITY_MODULE) {
        bind<HomePresenter>() with provider {
            HomePresenter(
                view = this@HomeActivity,
                errorHandler = instance(),
                executor = instance()
            )
        }
    }

    override fun initializeUI() {

    }

    override fun registerListeners() {

    }


    override fun showMessage(message: String) {

    }

    override fun showMessage(messageId: Int) {

    }


}