package ru.test.wallpapersfrompixabay.ui.adapters

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.images_row.view.*
import ru.test.wallpapersfrompixabay.models.Image
import java.io.IOException


class ImagesRowAdapter(private var images: List<Image>) :
    RecyclerView.Adapter<ImagesRowAdapter.ImagesRowViewHolder>() {

    private val TAG_ID = ImagesRowAdapter::class.java.simpleName

    fun setImages(images: List<Image>) {
        if (images.isEmpty()) return
        this.images = images
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImagesRowViewHolder(
            LayoutInflater.from(parent.context).inflate(
                ru.test.wallpapersfrompixabay.R.layout.images_row,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImagesRowViewHolder, position: Int) =
        holder.bind(images[position])

    inner class ImagesRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(image: Image) {
            Picasso.get().load(image.previewURL)
                .placeholder(ru.test.wallpapersfrompixabay.R.drawable.placeholder)
                .into(itemView.image_item_iv as ImageView)

            itemView.setOnClickListener {
                val builder =
                    AlertDialog.Builder(itemView.context)
                        .setTitle(ru.test.wallpapersfrompixabay.R.string.alert_title)
                        .setMessage(ru.test.wallpapersfrompixabay.R.string.alert_ok_dialog_msg)
                        .setNeutralButton(ru.test.wallpapersfrompixabay.R.string.alert_btn_cancel_name) { _, _ -> }
                        .setPositiveButton(ru.test.wallpapersfrompixabay.R.string.alert_btn_ok_name) { _, _ ->
                            getAndSetWallpaper(image.largeImageURL!!)
                        }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        private fun getAndSetWallpaper(imageUrl: String) {
            Picasso.get().load(imageUrl)
                .into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        Toast.makeText(
                            itemView.context,
                            itemView.context.getString(ru.test.wallpapersfrompixabay.R.string.alert_preparing_wallpaper_set),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onBitmapFailed(
                        e: Exception?,
                        errorDrawable: Drawable?
                    ) {
                        Toast.makeText(
                            itemView.context,
                            itemView.context.getString(ru.test.wallpapersfrompixabay.R.string.alert_failed_wallpaper_set),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onBitmapLoaded(
                        bitmap: Bitmap?,
                        from: Picasso.LoadedFrom?
                    ) {
                        val message = try {
                            WallpaperManager.getInstance(itemView.context)
                                .setBitmap(bitmap)
                            itemView.context.getString(ru.test.wallpapersfrompixabay.R.string.alert_success_wallpaper_set)
                        } catch (e: IOException) {
                            Log.d(TAG_ID, "FAILED to set wallpaper! ERROR: ${e}")
                            itemView.context.getString(ru.test.wallpapersfrompixabay.R.string.alert_failed_wallpaper_set)
                        }
                        Toast.makeText(
                            itemView.context,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
        }
    }

}