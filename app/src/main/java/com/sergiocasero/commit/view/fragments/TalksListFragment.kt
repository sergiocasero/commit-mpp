package com.sergiocasero.commit.view.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergiocasero.commit.R
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.navigator.AndroidNavigator
import com.sergiocasero.commit.common.presenter.TalksListPresenter
import com.sergiocasero.commit.common.presenter.TalksView
import com.sergiocasero.commit.view.adapter.SlotAdapter
import kotlinx.android.synthetic.main.fragment_talks_list.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class TalksListFragment : RootFragment<TalksView>(), TalksView {

    companion object {
        const val TRACK_ID_EXTRA = "TRACK_ID_EXTRA"
        fun newInstance(trackId: Long): TalksListFragment {
            val fragment = TalksListFragment()

            val bundle = Bundle()
            bundle.putLong(TRACK_ID_EXTRA, trackId)

            fragment.arguments = bundle
            return fragment
        }
    }

    override val progress: View by lazy { progressView }

    override val presenter: TalksListPresenter by instance()

    private val navigator: AndroidNavigator by instance()

    override val layoutResourceId: Int = R.layout.fragment_talks_list

    override val fragmentModule: Kodein.Module = Kodein.Module {
        bind<TalksListPresenter>() with provider {
            TalksListPresenter(
                view = this@TalksListFragment,
                errorHandler = instance(),
                executor = instance(),
                repository = instance(),
                navigator = instance()
            )
        }
    }

    private val slotAdapter by lazy {
        SlotAdapter { slot -> presenter.onSlotClicked(slot) }
    }

    override fun initializeUI() {
        slots.apply {
            adapter = slotAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun registerListeners() {
        // Nothing to do yet
    }

    override fun obtainTrackId(): Long = arguments?.getLong(TRACK_ID_EXTRA) ?: throw Exception()

    override fun showSlots(slots: List<Slot>) {
        slotAdapter.replace(slots)
    }

    override fun navigateToSlotDetail(slotId: Long) {
        navigator.navigateToSlotDetail(slotId)
    }
}
