package com.kvsoftware.sharefile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.FileProvider

class MainActivity : AppCompatActivity() {

    companion object {
        const val FILE_NAME = "super-mario-3d-world.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_share).setOnClickListener {
            shareFile()
        }
    }

    private fun shareFile() {
        val fileFromAssets = AssetHelper.getFile(this, FILE_NAME)
        fileFromAssets?.let {
            // Generate a file's content URI.
            val uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", it)

            // Send an Intent that contains the data.
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/jpeg"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(intent)
        }
    }
}