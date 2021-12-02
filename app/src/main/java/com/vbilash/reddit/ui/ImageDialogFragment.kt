package com.vbilash.reddit.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.vbilash.reddit.databinding.DialogFragmentImageBinding
import com.vbilash.reddit.model.Publication
import com.vbilash.reddit.utils.extensions.loadImageWithGlide

class ImageDialogFragment(
    private val publication: Publication
) : DialogFragment() {

    private lateinit var binding: DialogFragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentImageBinding.inflate(layoutInflater)
        val view = binding.root

        with(binding) {
            imageIncreasePublicImage.loadImageWithGlide(publication.image)
            imageIncreasePublicImage.setOnClickListener {
                dismiss()
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    companion object {
        //unique dialogs tag
        @JvmStatic
        val TAG: String = ImageDialogFragment::class.java.simpleName
    }
}