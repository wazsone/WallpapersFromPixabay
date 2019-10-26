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

    fun getImageHitsByCategory(category: String): LiveData<ImageHits> {
        job = Job()
        return object : LiveData<ImageHits>() {
            override fun onActive() {
                super.onActive()
                job?.let {
                    CoroutineScope(Dispatchers.IO + it).launch {
                        val images = try {
//                            val options = HashMap<String, String>()
//                            options["category"] = category
                            RetrofitBuilder.apiService.getImagesByCategory(category)
                        } catch (e: Exception) {
                            Log.d(
                                TAG_ID,
                                "FAILED TO EXECUTE RetrofitBuilder.apiService.getImagesByCategory(page)"
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