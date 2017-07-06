package omg.jd.tvmazeapiclient.components.main

import android.os.Bundle
import android.support.v4.content.Loader
import omg.jd.tvmazeapiclient.BaseActivity
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.mvp.PresenterLoader

class MainActivity : BaseActivity<MVPMain.View, MVPMain.Presenter>(), MVPMain.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MVPMain.Presenter> {
        return PresenterLoader(applicationContext, MainPresenterFactory())
    }
}