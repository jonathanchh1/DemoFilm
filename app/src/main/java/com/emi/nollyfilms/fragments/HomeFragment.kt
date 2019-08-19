package com.emi.nollyfilms.fragments

import android.app.Activity
import android.content.Context
import android.database.DatabaseUtils
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.emi.nollyfilms.R
import com.emi.nollyfilms.adapters.MovieAdapter
import com.emi.nollyfilms.api.MovieFinder.Companion.popular
import com.emi.nollyfilms.api.MovieFinder.Companion.topRated
import com.emi.nollyfilms.databinding.RecyclerviewContainerBinding
import com.emi.nollyfilms.di.injector
import com.emi.nollyfilms.model.Movies
import com.emi.nollyfilms.vm.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.recyclerview_container.*


class HomeFragment  : Fragment(){

    private lateinit var viewModel : HomeViewModel
    private lateinit var adapter : MovieAdapter
    private lateinit var gridLayout : GridLayoutManager
    private lateinit var binding : RecyclerviewContainerBinding
    private val popularMovies = popular
    private val Toprated = topRated
    private val STORED_KEY = "choice"
    private var sortBy : String = popularMovies
    private var recyclerState : Parcelable?=null
    private var disposable = Disposables.empty()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, injector.moviesViewModelFactory()).get(HomeViewModel::class.java)
        adapter = MovieAdapter()
        gridLayout = GridLayoutManager(context, resources.getInteger(R.integer.number))
        setHasOptionsMenu(true)
        if(savedInstanceState != null){
            if (savedInstanceState.containsKey(STORED_KEY)){
                sortBy = savedInstanceState.getString(STORED_KEY)!!
            }
        }
        sortChoice(sortBy)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.recyclerview_container, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.mrecyclerview.adapter = adapter
        binding.mrecyclerview.layoutManager = gridLayout
        updateUi()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
         outState.putParcelable("lmState", binding.mrecyclerview.layoutManager?.onSaveInstanceState())
         outState.putString(STORED_KEY, sortBy)
         super.onSaveInstanceState(outState)
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    private fun updateUi(){
       disposable =  viewModel.factoryList
           .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { list ->
                list.filter { it != null }.map {
                    if (list.snapshot().size > 0 && list != null) {
                        adapter.submitList(list)
                        adapter.notifyDataSetChanged()
                        //  Toast.makeText(context, "$list all data", Toast.LENGTH_SHORT).show()
                        Log.d(HomeFragment::class.java.simpleName, "$list all data")
                        if (recyclerState != null) {
                            binding.mrecyclerview.layoutManager?.onRestoreInstanceState(recyclerState)
                            recyclerState = null
                        }
                    }
                }
            },
                { error -> Log.e(HomeFragment::class.java.simpleName, "${error.printStackTrace()}")
            })


    }




    private fun sortChoice(sort : String) {
        Log.d(HomeFragment::class.java.simpleName, "$sort sorting")
       return viewModel.onFetch(sort)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      inflater.inflate(R.menu.main_menu, menu)
        when(sortBy){
           sortBy -> menu.findItem(R.id.popular).isChecked = true
           sortBy -> menu.findItem(R.id.top_rated).isChecked = true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.popular ->{
            Toast.makeText(context, "popular movies", Toast.LENGTH_SHORT).show()
            sortBy = popularMovies
            sortChoice(sortBy)
            item.isChecked = true
            true
        }
        R.id.top_rated ->{
            Toast.makeText(context, "top rated movies", Toast.LENGTH_SHORT).show()
            sortBy = Toprated
            sortChoice(sortBy)
            item.isChecked = true
            true
        }else ->
           super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }

    companion object{
        fun newInstance() : HomeFragment{
            return HomeFragment()
        }
    }
}