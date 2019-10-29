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
import kotlinx.android.synthetic.main.fragment_search_images.view.*
import ru.test.wallpapersfrompixabay.R
import ru.test.wallpapersfrompixabay.ui.MainActivity
import ru.test.wallpapersfrompixabay.ui.adapters.ImagesRowAdapter
import ru.test.wallpapersfrompixabay.utils.Utility
import ru.test.wallpapersfrompixabay.viewmodels.SearchViewModel

class SearchImagesFragment : Fragment() {

    private val adapter: ImagesRowAdapter = ImagesRowAdapter(mutableListOf())
    private lateinit var viewModel: SearchViewModel
    private lateinit var root: View
    private lateinit var lastQuery: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_search_images, container, false)
        init()
        return root
    }

    private fun init() {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        root.search__swipe_refresh_layout.setOnRefreshListener {
            viewModel.setQuery(lastQuery)
            root.search__swipe_refresh_layout.isRefreshing = false
        }

        val imageItemSizeInDp =
            this@SearchImagesFragment.resources.getDimension(R.dimen.image_item_iv_size) / this@SearchImagesFragment.resources.displayMetrics.density
        val gridSpinCount =
            Utility.calculateAmountOfColumns(context, imageItemSizeInDp)
        root.search__rv_images.layoutManager =
            StaggeredGridLayoutManager(gridSpinCount, StaggeredGridLayoutManager.VERTICAL)
        root.search__rv_images.itemAnimator = DefaultItemAnimator()
        root.search__rv_images.adapter = adapter

        root.search__button.setOnClickListener {
            lastQuery = root.search__edit_text.text.toString()
            viewModel.setQuery(lastQuery)
            (activity as MainActivity).hideKeyboard()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.imageHits.observe(this, Observer {
            adapter.setAndUpdateImages(it.hits)
        })
    }

    override fun onResume() {
        super.onResume()
        //TODO: It doesn't work =( Need to figure out why?
        root.search__edit_text.requestFocus()
        (activity as MainActivity).showKeyboard()
    }

    override fun onDestroyView() {
        viewModel.cancelJobs()
        (activity as MainActivity).hideKeyboard()
        super.onDestroyView()
    }

}
