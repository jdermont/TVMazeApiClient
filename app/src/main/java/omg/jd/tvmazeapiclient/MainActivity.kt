package omg.jd.tvmazeapiclient

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import omg.jd.tvmazeapiclient.components.main.MainFragment
import omg.jd.tvmazeapiclient.components.search.SearchActivity
import omg.jd.tvmazeapiclient.components.search.SearchFragment

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
        viewPager.adapter = MainViewPagerAdapter(supportFragmentManager)
    }

    class MainViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            if (position == 0) {
                return MainFragment()
            } else {
                return SearchFragment()
            }
        }

        override fun getCount(): Int = 2
    }
}
