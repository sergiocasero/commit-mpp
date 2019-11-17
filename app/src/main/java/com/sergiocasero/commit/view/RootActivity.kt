package com.sergiocasero.commit.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sergiocasero.commit.extension.hideMe
import com.sergiocasero.commit.extension.showMe
import com.sergiocasero.commit.extension.snackbar
import com.sergiocasero.commit.extension.toast
import com.sergiocasero.commit.common.presenter.Presenter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.subKodein
import android.view.ViewGroup


abstract class RootActivity<out V : Presenter.View> : AppCompatActivity(), KodeinAware, Presenter.View {

    abstract val progress: View

    abstract val presenter: Presenter<V>

    abstract val layoutResourceId: Int

    abstract val activityModule: Kodein.Module

    override val kodein by subKodein(kodein()) {
        import(activityModule)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)

        initializeUI()
        registerListeners()

        presenter.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    abstract fun initializeUI()

    abstract fun registerListeners()

    override fun showError(error: String) = toast(error)

    override fun showProgress() = progress.showMe()

    override fun hideProgress() = progress.hideMe()

    override fun showRetry(error: String, f: () -> Unit) {
        val viewGroup = (this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

        snackbar(viewGroup, message = error, retryCallback = { f() })
    }
}
