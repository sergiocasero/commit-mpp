package com.sergiocasero.commit.app

import android.app.Application
import com.sergiocasero.commit.di.appModule
import com.sergiocasero.commit.di.dataModule
import com.sergiocasero.commit.di.domainModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class CommitApp : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(appModule(this@CommitApp))
        import(domainModule)
        import(dataModule)
    }

}
