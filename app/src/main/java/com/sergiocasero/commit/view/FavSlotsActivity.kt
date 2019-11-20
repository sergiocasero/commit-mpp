package com.sergiocasero.commit.view

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergiocasero.commit.R
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.di.ACTIVITY_MODULE
import com.sergiocasero.commit.navigator.Navigator
import com.sergiocasero.commit.presenter.FavSlotsPresenter
import com.sergiocasero.commit.presenter.FavsSlotsView
import com.sergiocasero.commit.view.adapter.SlotAdapter
import kotlinx.android.synthetic.main.activity_fav_slots.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class FavSlotsActivity : RootActivity<FavsSlotsView>(), FavsSlotsView {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, FavSlotsActivity::class.java)
        }
    }

    override val progress: View by lazy { progressView }

    override val layoutResourceId: Int = R.layout.activity_fav_slots

    override val presenter: FavSlotsPresenter by instance()

    private val navigator: Navigator by instance()

    override val activityModule: Kodein.Module = Kodein.Module(ACTIVITY_MODULE) {
        bind<FavSlotsPresenter>() with provider {
            FavSlotsPresenter(
                repository = instance(),
                navigator = instance(),
                executor = instance(),
                errorHandler = instance(),
                view = this@FavSlotsActivity
            )
        }
    }

    private val slotAdapter by lazy {
        SlotAdapter { slot -> presenter.onSlotClicked(slot) }
    }

    override fun initializeUI() {
        favSlots.apply {
            adapter = slotAdapter
            layoutManager = LinearLayoutManager(context)
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun registerListeners() {
        // Nothing to do yet
    }

    override fun showSlots(slots: List<Slot>) {
        slotAdapter.replace(slots)
    }

    override fun navigateToSlotDetail(slotId: Long) {
        navigator.navigateToSlotDetail(slotId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}