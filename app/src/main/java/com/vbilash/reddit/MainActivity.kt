package com.vbilash.reddit

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
}