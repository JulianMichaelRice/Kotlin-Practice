package com.example.camerafilters

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
//import android.hardware.camera
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ImageView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var mCamera : Camera? = null
    lateinit var horizontalScrollView : HorizontalScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivCapture : ImageView = findViewById<ImageView>(R.id.ivCapture)
        val ivFilter : ImageView = findViewById<ImageView>(R.id.ivFilter)
        horizontalScrollView = findViewById(R.id.filterLayout)

        checkPermissionAndGive()

        ivCapture.setOnClickListener(this)
        ivFilter.setOnClickListener(this)
    }

    fun checkPermissionAndGive() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
//            checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            initialize();
//        }
        initialize();
    }

    private fun initialize() {
        //Grab our camera and the camera preview class
        mCamera = getCameraInstance();
        var mPreview : CameraPreview = CameraPreview(this, mCamera!!)
        var r1CamPreviewFrame : FrameLayout = findViewById(R.id.r1CameraPreview)

        //If we have a reference to the ID, set the camera preview to the frame layout
        if (r1CamPreviewFrame != null) {
            r1CamPreviewFrame.addView(mPreview)
        } else {
            Log.d("TAG", "ERROR!")
        }
    }

    private fun getCameraInstance(): Camera {
        //This function opens the camera. It's deprecated so I will adjust the code with ref. to below:
        //https://androidpedia.net/en/tutorial/619/camera-2-api "Preview the main camera in a TextureView"
        var c : Camera? = null

        try {
            c = Camera.open()
            Log.d("TAG", "Opened Camera!")
            return c
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d("TAG", "Failed to open Camera!")
        return Camera.open()
    }

    override fun onClick(v: View) {
        //When we click the cancel button (ivFilter), it should toggle the horizontal scrollbar
        when (v.id) {
            R.id.ivFilter -> {
                if (horizontalScrollView.visibility == View.VISIBLE) {
                    horizontalScrollView.visibility = View.GONE
                } else {
                    horizontalScrollView.visibility = View.VISIBLE
                }
            }
        }
    }

    fun colorEffectFilter(v: View) {
        if (mCamera != null) {
            var parameters: Camera.Parameters = mCamera!!.getParameters()
            //Depending on the clicked effect, the filter should change. DEPRECATED! >:(
            when (v.id) {
                R.id.ivEffectNone -> {
                    parameters.setColorEffect(Camera.Parameters.EFFECT_NONE)
                }
                R.id.ivEffectAqua -> {
                    parameters.setColorEffect(Camera.Parameters.EFFECT_AQUA)
                }
                R.id.ivEffectBlack -> {
                    parameters.setColorEffect(Camera.Parameters.EFFECT_MONO)
                }
            }
        }
    }
}