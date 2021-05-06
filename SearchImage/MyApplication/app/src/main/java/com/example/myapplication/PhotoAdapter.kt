package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout.view.*
import java.util.*


class PhotosAdapter(val requestBuilder: RequestBuilder<Drawable>, val preloadSizeProvider: ViewPreloadSizeProvider<Photo>,val parentAct :Activity, val photos: MutableList<Photo> = mutableListOf()) : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>(),ListPreloader.PreloadModelProvider<Photo> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photos[position])
        holder.itemView.setOnClickListener(View.OnClickListener { view ->
            val fullscreenIntent = Intent(this.parentAct,FullScreenActivity::class.java)
            fullscreenIntent.putExtra("photo",Gson().toJson(photos[position]))
            parentAct.startActivity(fullscreenIntent)
        })
    }

    inner class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: Photo) {

            Picasso.get().load(photo.url).centerCrop().resize(400,500).into(itemView.imageView)
        }
    }

    override fun getPreloadItems(position: Int): MutableList<Photo> {
        return Collections.singletonList(photos[position])
    }

    override fun getPreloadRequestBuilder(item: Photo): RequestBuilder<Drawable>? {
        return requestBuilder.load(item)
    }
}