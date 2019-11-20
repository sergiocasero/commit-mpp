package com.sergiocasero.commit.view

import android.view.Menu
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.sergiocasero.commit.R
import com.sergiocasero.commit.common.model.TrackItem
import com.sergiocasero.commit.di.ACTIVITY_MODULE
import com.sergiocasero.commit.models.DayView
import com.sergiocasero.commit.navigator.Navigator
import com.sergiocasero.commit.presenter.HomePresenter
import com.sergiocasero.commit.presenter.HomeView
import com.sergiocasero.commit.view.adapter.ViewPagerAdapter
import com.sergiocasero.commit.view.fragments.TalksListFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class HomeActivity : RootActivity<HomeView>(), HomeView {

    override val progress: View by lazy { progressView }

    override val presenter: HomePresenter by instance()

    override val layoutResourceId: Int = R.layout.activity_home

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager)
    }

    override val activityModule: Kodein.Module = Kodein.Module(ACTIVITY_MODULE) {
        bind<HomePresenter>() with provider {
            HomePresenter(
                repository = instance(),
                navigator = instance(),
                view = this@HomeActivity,
                errorHandler = instance(),
                executor = instance()
            )
        }
    }

    override fun initializeUI() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        viewPager.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = viewPagerAdapter.count
        }

        tab.setupWithViewPager(viewPager)
    }

    override fun registerListeners() {
        days.setOnNavigationItemSelectedListener {
            presenter.onDaySelected(it.itemId)
            true
        }
        fav.setOnClickListener { presenter.onFavClicked() }
    }

    override fun showDays(days: List<DayView>) {
        days.forEach { day ->
            this.days.menu.add(Menu.NONE, day.pos, Menu.NONE, day.title)
                .setIcon(R.drawable.ic_calendar_white_24dp)
            if (day.selected) {
                this.days.selectedItemId = day.pos
            }
        }
    }

    override fun showTracks(tracks: List<TrackItem>) {
        viewPagerAdapter.addFragments(tracks.map {
            Pair<String, Fragment>(
                it.name,
                TalksListFragment.newInstance(it.id)
            )
        })
    }
}
