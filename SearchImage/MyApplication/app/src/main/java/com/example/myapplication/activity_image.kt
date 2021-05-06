package com.example.myapplication

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.AbsListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import kotlinx.android.synthetic.main.activity_image.*
import java.text.FieldPosition


class activity_image : AppCompatActivity() {
    private val photosViewModel: PhotosViewModel by viewModels()
    private var flag = false
    private var currentItem = 0
    private var scrolledItem = 0
    private var  totalItem = 0
    private var page_counter = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val bundle : Bundle?  = intent.extras
        val message = bundle!!.get("message").toString()
        Log.d("Message",message)

        val layoutManager = GridLayoutManager(this,3)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        Log.d("Inside Main Thread :-", Thread.currentThread().name)
//        Glide.with(this).load

        val giftItemRequest : RequestBuilder<Drawable> = Glide.with(this).asDrawable()
        val preloadSizeProvider : ViewPreloadSizeProvider<Photo> = ViewPreloadSizeProvider()
        val photosAdapter = PhotosAdapter(giftItemRequest,preloadSizeProvider,this)
        recyclerView.adapter = photosAdapter
        val preloader : RecyclerViewPreloader<Photo> = RecyclerViewPreloader(Glide.with(this),photosAdapter,preloadSizeProvider,20)
        photosViewModel.photosLiveData.observe(this,
            Observer { list ->
                with(photosAdapter) {
//                    photos.clear()
                    photos.addAll(list)
                    notifyDataSetChanged()
                }
            })

        recyclerView.addOnScrollListener(preloader)
//        recyclerView.setNestedScrollingEnabled(false);
        photosViewModel.fetchImages(message,page_counter)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.d("Message ","Scroll state changed")
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    flag = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItem = layoutManager.childCount
                totalItem = layoutManager.itemCount
                scrolledItem = layoutManager.findFirstVisibleItemPosition()
                if(flag && (currentItem + scrolledItem == totalItem))
                {
                    Log.d("Message ","Data fetched")
                    flag = false
                    photosViewModel.fetchImages(message,page_counter)
                    page_counter += 1
                }
            }
        })

    }

}
