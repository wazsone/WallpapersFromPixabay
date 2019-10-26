package ru.test.wallpapersfrompixabay.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.test.wallpapersfrompixabay.models.ImageHits
import ru.test.wallpapersfrompixabay.repositories.MainRepository

class MainViewModel : ViewModel() {
    private val _category: MutableLiveData<String> = MutableLiveData()

    val imageHits: LiveData<ImageHits> = Transformations
        .switchMap(_category) {
            MainRepository.getImageHitsByCategory(it)
        }

    fun setCategory(update: String) {
        if (_category.value == update) {
            return
        }
        _category.value = update
    }

    fun cancelJobs() {
        MainRepository.cancelJobs()
    }
}