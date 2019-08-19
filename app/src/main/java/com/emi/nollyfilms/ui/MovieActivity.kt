package com.emi.nollyfilms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.emi.nollyfilms.R
import com.emi.nollyfilms.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            showFragment(HomeFragment.newInstance())
        }

        setSupportActionbar(getString(R.string.title))

    }




    private fun setSupportActionbar(title : String){
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }
    private fun showFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
         transaction.replace(R.id.fragmentHolder, fragment)
         transaction.commitNow()
    }
}
