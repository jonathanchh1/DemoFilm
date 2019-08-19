package com.emi.nollyfilms.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.emi.nollyfilms.R
import com.emi.nollyfilms.databinding.ActivityDetailBinding
import com.emi.nollyfilms.fragments.DetailFragment
import com.emi.nollyfilms.fragments.DetailFragment.Companion.DetailMovies
import com.emi.nollyfilms.model.Movies
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val detailFragment = DetailFragment.newInstance()
    private lateinit  var movies : Movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.activity = this
        movies = DetailMovies(intent, this)
        setSupportActionBar()
        if(savedInstanceState == null) {
            showFragment(detailFragment)
        }

    }

    fun detailThumb() = movies.detail_thumbnail

    private fun setSupportActionBar(){
        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showFragment(fragment: Fragment){
       val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.detail_container, fragment)
        transaction.commitNow()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
