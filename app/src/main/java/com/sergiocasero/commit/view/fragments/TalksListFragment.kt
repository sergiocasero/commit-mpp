package com.sergiocasero.commit.view.fragments

import android.os.Bundle
import android.view.View
import com.sergiocasero.commit.R
import com.sergiocasero.commit.presenter.TalksListPresenter
import kotlinx.android.synthetic.main.fragment_talks_list.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class TalksListFragment : RootFragment<TalksListPresenter.View>(), TalksListPresenter.View {

    companion object {
        const val TRACK_ID_EXTRA = "TRACK_ID_EXTRA"
        fun newInstance(trackId: Int): TalksListFragment {
            val fragment = TalksListFragment()

            val bundle = Bundle()
            bundle.putInt(TRACK_ID_EXTRA, trackId)

            fragment.arguments = bundle
            return fragment
        }
    }

    override val progress: View by lazy { TODO() }

    override val presenter: TalksListPresenter by instance()

    override val layoutResourceId: Int = R.layout.fragment_talks_list

    override val fragmentModule: Kodein.Module = Kodein.Module {
        bind<TalksListPresenter>() with provider {
            TalksListPresenter(
                view = this@TalksListFragment,
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

    override fun obtainTrackId(): Int = arguments?.getInt(TRACK_ID_EXTRA) ?: throw Exception()

    override fun showTrackId(id: String) {
        trackId.text = id
    }


}