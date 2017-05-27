package omg.jd.tvmazeapiclient

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import omg.jd.tvmazeapiclient.components.search.SearchActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        val intent = Intent(this,SearchActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun initViews() {

    }

 }
