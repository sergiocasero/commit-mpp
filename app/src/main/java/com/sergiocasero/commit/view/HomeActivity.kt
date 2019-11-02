package com.sergiocasero.commit.view

import android.view.View
import com.sergiocasero.commit.R
import androidx.appcompat.widget.Toolbar
import com.sergiocasero.commit.di.ACTIVITY_MODULE
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
    }

    override fun registerListeners() {

    }


    override fun showMessage(message: String) {

    }

    override fun showMessage(messageId: Int) {

    }

    override fun showTracks(tracks: Int) {
        for (i in 0..tracks) {
            viewPagerAdapter.addFragment("Track $i", TalksListFragment.newInstance(i))
        }
        viewPager.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = viewPagerAdapter.count
        }
        tab.setupWithViewPager(viewPager)
    }

}