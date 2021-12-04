package com.vbilash.reddit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vbilash.reddit.adapter.PublicationAdapter
import com.vbilash.reddit.adapter.PublicationClickListener
import com.vbilash.reddit.database.PublicationService
import com.vbilash.reddit.databinding.ActivityMainBinding
import com.vbilash.reddit.model.Publication
import com.vbilash.reddit.viewmodel.PublicationViewModel
import com.vbilash.reddit.viewmodel.PublicationViewModelFactory
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PublicationAdapter
    private lateinit var viewModel: PublicationViewModel

    private val listener = object : PublicationClickListener {
        override fun openImage(publication: Publication) {
            val dialog = ImageDialogFragment(publication)
            dialog.show(supportFragmentManager, ImageDialogFragment.TAG)
        }

        override fun downloadImage(publication: Publication) {
            downloadImageToStorage(publication.image)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val publicationsList: MutableList<Publication> = PublicationService().getPublicationsList()
        viewModel = ViewModelProvider(this, PublicationViewModelFactory(publicationsList)).get(
            PublicationViewModel::class.java
        )

        initRecycler()
        viewModel.publicationListLiveData.observe(this, {
            adapter = PublicationAdapter(it as MutableList<Publication>, listener)
            recyclerView.adapter = adapter
        })
    }

    private fun initRecycler() {
        recyclerView = binding.recyclerViewPublication
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun downloadImageToStorage(image: String) {
        val dirName = "download"
        val dirPath = getExternalFilesDir(null)
        val dir = File(dirPath, dirName)

        Glide.with(this)
            .asBitmap()
            .load(image)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val fileName = image.substring(image.length - 5) + ".jpg"
                    saveImage(resource, dir, fileName)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun saveImage(bitmap: Bitmap, dir: File, fileName: String) {
        if (!dir.exists()) {
            dir.mkdir()
        }
        val imageFile = File(dir, fileName)
        try {
            val fOut: OutputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.close()

            MediaScannerConnection.scanFile(
                this@MainActivity,
                arrayOf(imageFile.toString()),
                null,
                null
            )
        } catch (exception: Exception) {
            Toast.makeText(baseContext, "Error while saving image", Toast.LENGTH_SHORT).show()
            exception.printStackTrace()
        }
    }
}