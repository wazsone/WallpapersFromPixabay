package ru.test.wallpapersfrompixabay.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.test.wallpapersfrompixabay.R
import ru.test.wallpapersfrompixabay.ui.adapters.ImagesRowAdapter
import ru.test.wallpapersfrompixabay.utils.Utility
import ru.test.wallpapersfrompixabay.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val TAG_ID = MainActivity::class.java.simpleName
    private lateinit var viewModel: MainViewModel
    private val adapter: ImagesRowAdapter = ImagesRowAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        main__swipe_refresh_layout.setOnRefreshListener {
            main__swipe_refresh_layout.isRefreshing = false
        }
        val imageItemSizeInDp =
            this.resources.getDimension(R.dimen.image_item_iv_size) / this.resources.displayMetrics.density
        val gridSpinCount = Utility.calculateNoOfColumns(this@MainActivity, imageItemSizeInDp)
        main__rv_images.layoutManager =
            StaggeredGridLayoutManager(gridSpinCount, StaggeredGridLayoutManager.VERTICAL)
        main__rv_images.itemAnimator = DefaultItemAnimator()

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)
        viewModel.imageHits.observe(this@MainActivity, Observer {
            adapter.setImages(it.hits ?: listOf())
            main__rv_images.adapter = adapter
            adapter.notifyDataSetChanged()
        })
        viewModel.setPage(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }
}
