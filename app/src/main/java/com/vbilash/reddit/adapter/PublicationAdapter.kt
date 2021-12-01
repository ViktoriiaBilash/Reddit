package com.vbilash.reddit.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vbilash.reddit.databinding.PublicationBinding
import com.vbilash.reddit.model.Publication
import androidx.recyclerview.widget.DiffUtil

class PublicationAdapter (private var publicationList : MutableList <Publication>) : RecyclerView.Adapter<PublicationAdapter.PublicationViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    class PublicationsDiffCallback(
    private val oldList: List<Publication>,
    private val newList: List<Publication>
    ) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]== newList[newItemPosition]
        }
    }


    class PublicationViewHolder(
        private val binding : PublicationBinding
    ): RecyclerView.ViewHolder(binding.root)

}