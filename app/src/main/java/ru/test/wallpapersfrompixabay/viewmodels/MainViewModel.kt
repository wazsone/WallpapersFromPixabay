package ru.test.wallpapersfrompixabay.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.test.wallpapersfrompixabay.models.ImageHits
import ru.test.wallpapersfrompixabay.repositories.MainRepository

class MainViewModel : ViewModel() {
    private val _page: MutableLiveData<Int> = MutableLiveData()

    val imageHits: LiveData<ImageHits> = Transformations
        .switchMap(_page) {
            MainRepository.getImageHits(it)
        }

    fun setPage(update: Int) {
        if (_page.value == update) {
            return
        }
        _page.value = update
    }

    fun cancelJobs() {
        MainRepository.cancelJobs()
    }
}