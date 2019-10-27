package ru.test.wallpapersfrompixabay.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import ru.test.wallpapersfrompixabay.api.RetrofitBuilder
import ru.test.wallpapersfrompixabay.models.ImageHits
import ru.test.wallpapersfrompixabay.ui.MainActivity

object MainRepository {
    private val TAG_ID = MainActivity::class.java.simpleName
    var job: CompletableJob? = null

    fun getImageHits(category: String = "", query: String = ""): LiveData<ImageHits> {
        cancelJobs()
        job = Job()
        return object : LiveData<ImageHits>() {
            override fun onActive() {
                super.onActive()
                job?.let {
                    CoroutineScope(Dispatchers.IO + it).launch {
                        val options = HashMap<String, String>()
                        options["per_page"] = "200"
                        options["category"] = category
                        options["q"] = query
                        val images = try {
                            RetrofitBuilder.apiService.getImages(options)
                        } catch (e: Exception) {
                            Log.d(
                                TAG_ID,
                                "FAILED TO EXECUTE RetrofitBuilder.apiService.getImages(options = ${options})"
                            )
                            ImageHits()
                        }
                        withContext(Dispatchers.Main) {
                            value = images
                            it.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }
}