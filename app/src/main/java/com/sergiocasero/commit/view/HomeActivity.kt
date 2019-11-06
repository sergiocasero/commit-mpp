package com.sergiocasero.commit.view

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.sergiocasero.commit.R
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

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment("Track 1", TalksListFragment.newInstance(1))
        viewPagerAdapter.addFragment("Track 2", TalksListFragment.newInstance(2))
        viewPagerAdapter.addFragment("Track 3", TalksListFragment.newInstance(3))
        viewPagerAdapter.addFragment("Track 4", TalksListFragment.newInstance(4))
        viewPagerAdapter.addFragment("Track 5", TalksListFragment.newInstance(5))

        viewPager.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = viewPagerAdapter.count
        }
        tab.setupWithViewPager(viewPager)
    }

    override fun registerListeners() {

    }
}
