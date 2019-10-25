package ru.test.wallpapersfrompixabay.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.images_row.view.*
import ru.test.wallpapersfrompixabay.R
import ru.test.wallpapersfrompixabay.models.Image

class ImagesRowAdapter(private var images: List<Image>) :
    RecyclerView.Adapter<ImagesRowAdapter.ImagesRowViewHolder>() {

    fun setImages(images: List<Image>) {
        this.images = images
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImagesRowViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.images_row,
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
                .placeholder(R.drawable.placeholder)
                .into(itemView.image_item_iv as ImageView)
        }
    }

}