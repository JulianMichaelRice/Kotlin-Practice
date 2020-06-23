package com.example.camerafilters

import android.content.Context
import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.io.IOException

class CameraPreview(context: Context, mCamera: Camera) : SurfaceView(context), SurfaceHolder.Callback {
    lateinit var mHolder : SurfaceHolder
    lateinit var mCamera : Camera

    init {
        this.mCamera = mCamera
        this.mHolder = holder
        mHolder.addCallback(this)
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            var parameters : Camera.Parameters = mCamera.getParameters()
            parameters.set("orientation", "portrait")
            mCamera.setParameters(parameters)
            mCamera.setDisplayOrientation(90)
            mCamera.setPreviewDisplay(holder)
            mCamera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (mHolder.surface == null) {
            return
        } else {
            mCamera.stopPreview()
            mCamera.setPreviewDisplay(mHolder)
            mCamera.startPreview()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }
}
