package com.kvsoftware.sharefile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide

class FileSelectActivity : AppCompatActivity() {

    companion object {
        const val MARIO_FILE_NAME = "mario.jpg"
        const val LUIGI_FILE_NAME = "luigi.jpg"
    }

    private lateinit var imageViewMario: ImageView
    private lateinit var imageViewLuigi: ImageView
    private lateinit var buttonMario: Button
    private lateinit var buttonLuigi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_select)
        initializeView()
        buttonMario.setOnClickListener { shareFile(MARIO_FILE_NAME) }
        buttonLuigi.setOnClickListener { shareFile(LUIGI_FILE_NAME) }
    }

    private fun initializeView() {
        imageViewMario = findViewById(R.id.imageview_mario)
        imageViewLuigi = findViewById(R.id.imageview_luigi)
        buttonMario = findViewById(R.id.button_mario)
        buttonLuigi = findViewById(R.id.button_luigi)

        Glide.with(this).load(AssetHelper.getFile(this, MARIO_FILE_NAME)).into(imageViewMario)
        Glide.with(this).load(AssetHelper.getFile(this, LUIGI_FILE_NAME)).into(imageViewLuigi)
    }

    private fun shareFile(fileName: String) {
        val resultIntent = Intent("${packageName}.ACTION_RETURN_FILE")

        val fileFromAssets = AssetHelper.getFile(this, fileName)
        if (fileFromAssets == null) {
            resultIntent.setDataAndType(null, "")
            setResult(RESULT_CANCELED, resultIntent)
            return
        }

        // Generate a file's content URI.
        val fileUri = FileProvider
            .getUriForFile(this, "${packageName}.fileprovider", fileFromAssets)

        // Send an Intent that contains the data.
        resultIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        resultIntent.setDataAndType(fileUri, contentResolver.getType(fileUri))
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

}