package omg.jd.tvmazeapiclient.components.main

import android.content.SharedPreferences
import omg.jd.tvmazeapiclient.mvp.PresenterFactory

class MainPresenterFactory(val pref: SharedPreferences) : PresenterFactory<MVPMain.Presenter> {
    override fun create(): MVPMain.Presenter {
        val interactor: MainInteractor = MainInteractor(pref)
        return MainPresenter(interactor)
    }
}
