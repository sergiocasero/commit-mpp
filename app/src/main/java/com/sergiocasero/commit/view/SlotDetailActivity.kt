package com.sergiocasero.commit.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergiocasero.commit.R
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.di.ACTIVITY_MODULE
import com.sergiocasero.commit.extension.animateChild
import com.sergiocasero.commit.presenter.SlotDetailPresenter
import com.sergiocasero.commit.presenter.SlotDetailView
import com.sergiocasero.commit.view.adapter.SpeakerAdapter
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
                navigator = instance(),
                executor = instance(),
                errorHandler = instance(),
                view = this@SlotDetailActivity
            )
        }
    }

    private val speakerAdapter = SpeakerAdapter { presenter.onSpeakerTwitterClick(it) }

    override fun initializeUI() {
        with(speakers) {
            adapter = speakerAdapter
            layoutManager = LinearLayoutManager(this@SlotDetailActivity)
            isNestedScrollingEnabled = false
        }

    }

    override fun registerListeners() {
        // Nothing to do yet
    }

    // TODO Change this hardcoded number
    override fun getSlotId(): Long = 387404009

    override fun showSlot(slot: Slot) {
        container.animateChild()

        time.text = getString(R.string.slot_time, slot.start, slot.end)

        slot.contents?.let { contents ->
            description.text = contents.description
            toolbar.title = contents.title
            setSupportActionBar(toolbar)

            speakerAdapter.clear()
            speakerAdapter.addAll(contents.speakers)
        }

    }
}
