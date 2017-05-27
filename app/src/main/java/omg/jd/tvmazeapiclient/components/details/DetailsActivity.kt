package omg.jd.tvmazeapiclient.components.details

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details.*

import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.utils.loadUrl

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TVSHOW = "EXTRA_TVSHOW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        detailsShowImage.loadUrl(intent.extras.getString(EXTRA_TVSHOW))
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}
