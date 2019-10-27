package ru.test.wallpapersfrompixabay.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.test.wallpapersfrompixabay.models.ImageHits
import ru.test.wallpapersfrompixabay.repositories.MainRepository

class SearchViewModel : ViewModel() {
    private val _query: MutableLiveData<String> = MutableLiveData()

    val imageHits: LiveData<ImageHits> = Transformations
        .switchMap(_query) {
            MainRepository.getImageHits(query = it)
        }

    fun setQuery(update: String) {
        if (_query.value == update) {
            return
        }
        _query.value = update
    }

    fun cancelJobs() {
        Log.d("ViewModels", "SearchViewModel, fun cancelJobs(), _query.value = ${_query.value}")
        MainRepository.cancelJobs()
    }

}