package ru.test.wallpapersfrompixabay.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import ru.test.wallpapersfrompixabay.R
import ru.test.wallpapersfrompixabay.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val TAG_ID = MainActivity::class.java.simpleName
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)
        viewModel.imageHits.observe(this@MainActivity, Observer {
            Log.d(TAG_ID, "${it?.hits}")
            main__tv.text = it?.hits.toString()
        })
        viewModel.setPage(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }
}
