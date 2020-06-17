package com.example.application1

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class DisplayImage : AppCompatActivity() {
    lateinit var imageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)
        imageView = findViewById(R.id.image_toShow)

        var bitmap = BitmapFactory.decodeFile(intent.getStringExtra("image_path"))
        imageView.setImageBitmap(bitmap)
    }
}