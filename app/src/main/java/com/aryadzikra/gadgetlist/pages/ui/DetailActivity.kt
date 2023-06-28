package com.aryadzikra.gadgetlist.pages.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.aryadzikra.gadgetlist.R
import com.aryadzikra.gadgetlist.databinding.ActivityDetailBinding
import com.aryadzikra.gadgetlist.model.GadgetModel
import com.aryadzikra.gadgetlist.utils.GADGET_KEY

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity(R.layout.activity_detail) {
    private val binding : ActivityDetailBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarDetail)
        val gadgetInfo = intent.extras?.getParcelable<GadgetModel>(GADGET_KEY) as GadgetModel

        setButtons(gadgetInfo)
        setValues(gadgetInfo)
    }

    private fun setButtons(gadgetInfo: GadgetModel) {
        binding.toolbarDetail.setNavigationOnClickListener {
            finish()
        }

        binding.actionShare.setOnClickListener {
            shareAction(gadgetInfo)
        }

        binding.btnWatchVideo.setOnClickListener {
            openVideo(gadgetInfo)
        }
    }

    private fun shareAction(gadgetInfo : GadgetModel) {
        val shareIntent = Intent()
        shareIntent.apply {
            type = "text/plain"
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, gadgetInfo)
        }
        startActivity(Intent.createChooser(shareIntent, "Share ${gadgetInfo.name} via..."))
    }

    private fun openVideo(gadgetInfo: GadgetModel) {
        val videoIntent = Intent()
        videoIntent.data = gadgetInfo.videoUrl?.toUri()
        startActivity(videoIntent)
    }

    @SuppressLint("SetTextI18n")
    private fun setValues(gadgetInfo: GadgetModel) {
        binding.detailImg.load(gadgetInfo.imageUrl){
            crossfade(true)
            placeholder(R.drawable.baseline_image_24)
        }
        binding.tvDescription.text = gadgetInfo.description
        binding.tvRating.text = "Rating : ${gadgetInfo.rating}"
        binding.tvPrice.text = "Harga : ${gadgetInfo.price}"
        binding.toolbarDetail.title = gadgetInfo.name
        binding.tvCategory.text = gadgetInfo.category
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}