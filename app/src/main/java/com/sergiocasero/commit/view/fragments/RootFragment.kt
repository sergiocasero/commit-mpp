package com.sergiocasero.commit.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sergiocasero.commit.extension.hideMe
import com.sergiocasero.commit.extension.showMe
import com.sergiocasero.commit.extension.snackbar
import com.sergiocasero.commit.extension.toast
import com.sergiocasero.commit.common.presenter.Presenter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.android.subKodein


/**
 * RootFragment
 */
abstract class RootFragment<out V : Presenter.View> : Fragment(), KodeinAware, Presenter.View {

    abstract val progress: View

    abstract val presenter: Presenter<V>

    abstract val layoutResourceId: Int

    abstract val fragmentModule: Kodein.Module

    override val kodein by subKodein(kodein()) {
        import(fragmentModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI()
        registerListeners()

        presenter.attach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutResourceId, container, false)


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
        view?.let { activity?.snackbar(it, message = error, retryCallback = { f() }) }
    }
}
