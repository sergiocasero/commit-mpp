package com.sergiocasero.commit.view

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
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

    companion object {
        private const val SLOT_ID_EXTRA = "SLOT_ID_EXTRA"
        fun getCallingIntent(context: Context, slotId: Long): Intent {
            val callingIntent = Intent(context, SlotDetailActivity::class.java)
            callingIntent.putExtra(SLOT_ID_EXTRA, slotId)
            return callingIntent
        }
    }

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

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun registerListeners() {
        fav.setOnClickListener { presenter.onFavClick() }
    }

    override fun getSlotId(): Long = intent?.extras?.getLong(SLOT_ID_EXTRA) ?: throw IllegalArgumentException()

    override fun showSlot(slot: Slot) {
        container.animateChild()

        time.text = getString(R.string.slot_time, slot.start, slot.end)

        slot.contents?.let { contents ->
            description.text = contents.description
            toolbar.title = contents.title

            speakerAdapter.clear()
            speakerAdapter.addAll(contents.speakers)
        }
    }

    override fun showFavUI(isFav: Boolean) {
        val textColor = ContextCompat.getColor(this, R.color.white)

        val backgroundColor = ContextCompat.getColor(
            this, when (isFav) {
                true -> R.color.red_800
                false -> R.color.grey_500
            }
        )

        fav.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        fav.imageTintList = ColorStateList.valueOf(textColor)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
