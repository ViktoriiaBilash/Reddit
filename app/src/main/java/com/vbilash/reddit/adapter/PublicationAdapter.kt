package com.vbilash.reddit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vbilash.reddit.databinding.PublicationBinding
import com.vbilash.reddit.model.Publication
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

        with(holder.binding) {
            imagePublicPhoto.setOnClickListener {
                listener.openImage(matchPublication(holder))
            }
            buttonDownload.setOnClickListener {
                listener.downloadImage(matchPublication(holder))
            }
        }
        return holder
    }

    private fun matchPublication(holder: PublicationViewHolder): Publication {
        val position = holder.absoluteAdapterPosition
        return publicationList[position]
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val publicationItem = publicationList[position]
        val comments = publicationItem.commentsNum

        with(holder.binding) {
            textViewName.text = publicationItem.userName
            textViewPublicText.text = publicationItem.text
            buttonComment.text = "$comments comments"
            imagePublicPhoto.loadImageWithGlide(publicationItem.image)
            imageAvatar.loadImageWithGlideCircle(publicationItem.icon)
        }
    }

    override fun getItemCount(): Int {
        return publicationList.size
    }

    class PublicationViewHolder(
        val binding: PublicationBinding
    ) : RecyclerView.ViewHolder(binding.root)
}