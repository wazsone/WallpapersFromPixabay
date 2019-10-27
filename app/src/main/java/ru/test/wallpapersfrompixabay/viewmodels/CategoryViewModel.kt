package ru.test.wallpapersfrompixabay.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.test.wallpapersfrompixabay.models.ImageHits
import ru.test.wallpapersfrompixabay.repositories.MainRepository

class CategoryViewModel : ViewModel() {
    private val _category: MutableLiveData<String> = MutableLiveData()

    val imageHits: LiveData<ImageHits> = Transformations
        .switchMap(_category) {
            MainRepository.getImageHits(category = it)
        }

    fun setCategory(update: String) {
        if (_category.value == update) {
            return
        }
        _category.value = update
    }

    fun cancelJobs() {
        Log.d(
            "ViewModels",
            "CategoryViewModel, fun cancelJobs(), _category.value = ${_category.value}"
        )
        MainRepository.cancelJobs()
    }

}