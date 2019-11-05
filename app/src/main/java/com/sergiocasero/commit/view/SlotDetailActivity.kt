package com.sergiocasero.commit.view

import android.view.View
import com.sergiocasero.commit.R
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.di.ACTIVITY_MODULE
import com.sergiocasero.commit.presenter.Presenter
import com.sergiocasero.commit.presenter.SlotDetailPresenter
import com.sergiocasero.commit.presenter.SlotDetailView
import kotlinx.android.synthetic.main.activity_slot_detail.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class SlotDetailActivity : RootActivity<SlotDetailView>(), SlotDetailView {

    override val progress: View by lazy { progressView }
    override val layoutResourceId: Int = R.layout.activity_slot_detail
    override val presenter: SlotDetailPresenter by instance()
    override val activityModule: Kodein.Module = Kodein.Module(ACTIVITY_MODULE) {
        bind<SlotDetailPresenter>() with provider {
            SlotDetailPresenter(
                repository = instance(),
                executor = instance(),
                errorHandler = instance(),
                view = this@SlotDetailActivity
            )
        }
    }

    override fun initializeUI() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerListeners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(messageId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSlotId(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSlot(slot: Slot) {
        println(slot)
    }
}
