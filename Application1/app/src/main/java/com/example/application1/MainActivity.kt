package com.example.application1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var currentImagePath: String? = null
    val IMAGE_REQUEST: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton = findViewById<Button>(R.id.rollButton)
        val resultsTextView = findViewById<TextView>(R.id.resultsTextView)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        rollButton.setOnClickListener {
            val rand = Random().nextInt(seekBar.progress)
            resultsTextView.text = rand.toString()
        }
    }

    fun takePicture(view: View) {
        var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(packageManager) != null) {
            var imageFile: File
            try {
                imageFile = getImageFile()
                var imageUri:Uri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", imageFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(cameraIntent, IMAGE_REQUEST)
            } catch (e: IOException) {
                println("ERROR!")
                println(e.printStackTrace())
            }
        }
    }

    fun showPicture(view: View) {
        var intent : Intent = Intent(this, DisplayImage::class.java)
        intent.putExtra("image_path", currentImagePath)
        startActivity(intent)
    }

    fun getImageFile() : File {
        var timeStamp = SimpleDateFormat("yyyyMMdd").format(Date())
        var imageName = "JPG " + timeStamp + "_"
        var storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var imageFile = File.createTempFile(imageName, ".jpg", storageDir)
        currentImagePath = imageFile.absolutePath
        return imageFile
    }
}