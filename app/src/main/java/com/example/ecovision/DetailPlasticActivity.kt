package com.example.ecovision

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class DetailPlasticActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_plastic)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Jenis Plastik"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val plasticType: PlasticType? = intent.getParcelableExtra("plastic_type")

        plasticType?.let {
            findViewById<ImageView>(R.id.imageViewPlasticDetail).setImageResource(it.imageResId)
            findViewById<TextView>(R.id.textViewPlasticNameDetail).text = it.name
            findViewById<ImageView>(R.id.imageViewPlasticSymbol).setImageResource(it.imageIcon)
            findViewById<TextView>(R.id.textViewDescriptionDetail).text = it.description
            findViewById<TextView>(R.id.textViewRecyclingProcessDescription).text = it.recyclingProcess
            findViewById<TextView>(R.id.textViewEnvironmentalImpactDescription).text = it.environmentalImpact

            // Initialize ImageSlider for example images
            val imageSlideshow = findViewById<ImageSlider>(R.id.imageSlideshow)
            val imageList = ArrayList<SlideModel>()

            it.examples.forEach { imageResId ->
                imageList.add(SlideModel(imageResId, ScaleTypes.CENTER_CROP))
            }

            imageSlideshow.setImageList(imageList, ScaleTypes.CENTER_CROP)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
