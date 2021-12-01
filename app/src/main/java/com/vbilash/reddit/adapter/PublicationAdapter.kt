package com.vbilash.reddit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.vbilash.reddit.databinding.PublicationBinding
import com.vbilash.reddit.model.Publication
import androidx.recyclerview.widget.DiffUtil
import com.vbilash.reddit.R
import com.vbilash.reddit.utils.extensions.loadImageWithGlide
import com.vbilash.reddit.utils.extensions.loadImageWithGlideCircle

class PublicationAdapter(
    private var publicationList: MutableList<Publication>,
    private val listener: PublicationClickListener
) : RecyclerView.Adapter<PublicationAdapter.PublicationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding: PublicationBinding = PublicationBinding.inflate(
            view, parent, false
        )
        val holder = PublicationViewHolder(binding)

        holder.binding.imagePublicPhoto.setOnClickListener {
            val position = holder.absoluteAdapterPosition
            val model = publicationList[position]
            listener.openImage(model)
        }
        return holder
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val publicationItem = publicationList[position]

        holder.binding.textViewName.text = publicationItem.userName
        val time = publicationItem.publicationTime.toString()
        holder.binding.textViewTime.text = "Posted $time hours ago"
        holder.binding.textViewPublicText.text = publicationItem.text
        val comments = publicationItem.commentsNum.toString()
        holder.binding.buttonComment.text = "$comments comments"

        if (publicationItem.image != null) {
            holder.binding.imagePublicPhoto.loadImageWithGlide(publicationItem.image)
        } else {
            holder.binding.imagePublicPhoto.isVisible = false
        }

        if (publicationItem.icon != null) {
            holder.binding.imageAvatar.loadImageWithGlideCircle(publicationItem.icon)
        } else {
            holder.binding.imageAvatar.setImageResource(R.drawable.ic_avatar)
        }
    }

    override fun getItemCount(): Int {
        return publicationList.size
    }

    fun updateList(newList: List<Publication>) {
        val diffCallback = PublicationsDiffCallback(publicationList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    class PublicationsDiffCallback(
        private val oldList: List<Publication>,
        private val newList: List<Publication>
    ) : DiffUtil.Callback() {
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
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    class PublicationViewHolder(
        val binding: PublicationBinding
    ) : RecyclerView.ViewHolder(binding.root)

}