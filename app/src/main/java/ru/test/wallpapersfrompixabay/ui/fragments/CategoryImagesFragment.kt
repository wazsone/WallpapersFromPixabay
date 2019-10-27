package ru.test.wallpapersfrompixabay.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_category_images.view.*
import ru.test.wallpapersfrompixabay.R
import ru.test.wallpapersfrompixabay.ui.adapters.ImagesRowAdapter
import ru.test.wallpapersfrompixabay.utils.Utility
import ru.test.wallpapersfrompixabay.viewmodels.CategoryViewModel

abstract class CategoryImagesFragment(private val category: String) : Fragment() {

    private lateinit var viewModel: CategoryViewModel
    private val adapter: ImagesRowAdapter = ImagesRowAdapter(mutableListOf())
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_category_images, container, false)
        init()
        return root
    }

    private fun init() {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        root.category__swipe_refresh_layout.setOnRefreshListener {
            viewModel.setCategory(category)
            root.category__swipe_refresh_layout.isRefreshing = false
        }
        val imageItemSizeInDp =
            this.resources.getDimension(R.dimen.image_item_iv_size) / this.resources.displayMetrics.density

        val gridSpinCount =
            Utility.calculateAmountOfColumns(activity!!.applicationContext, imageItemSizeInDp)
        root.category__rv_images.layoutManager =
            StaggeredGridLayoutManager(gridSpinCount, StaggeredGridLayoutManager.VERTICAL)
        root.category__rv_images.itemAnimator = DefaultItemAnimator()
        root.category__rv_images.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        viewModel.imageHits.observe(this, Observer {
            adapter.setAndUpdateImages(it.hits)
        })
        viewModel.setCategory(category)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }

}