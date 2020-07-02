package com.cahstudio.gridimage.ui.detail

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cahstudio.gridimage.R
import com.cahstudio.gridimage.model.Meme
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_meme.*
import java.io.File


class DetailMemeActivity : AppCompatActivity(), View.OnClickListener {
    private var path = ""
    private  var mBitmap: Bitmap? = null

    private lateinit var meme: Meme
    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meme)

        pref = getSharedPreferences("Mim", Context.MODE_PRIVATE)
        editor = getSharedPreferences("Mim", Context.MODE_PRIVATE)?.edit()!!

        if (intent != null){
            val meme = intent.getParcelableExtra<Meme>("meme")
            if (meme != null){
                this.meme = meme
                Picasso.get().load(meme.url).into(detail_ivMeme)
                checkData(meme)
            }
        }

        detail_btnAddLogo.setOnClickListener(this)
        detail_btnAddText.setOnClickListener(this)
        detail_btnSimpan.setOnClickListener(this)
        detail_btnBatal.setOnClickListener(this)
    }

    fun checkData(meme: Meme){
        val text = pref.getString(meme.id+"_text","")
        val path = pref.getString(meme.id+"_path","")
        detail_tvText.text = text

        if (path != null && path.isNotEmpty()){
            showLogo(path)
        }
    }

    fun showLogo(path: String){
        val imgFile = File(path)

        if (imgFile.exists()) {
            val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            detail_ivLogo.setImageBitmap(bitmap)
        }
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    123
                )
        }else{
            moveToGallery()
        }
    }

    fun moveToGallery(){
        var pictureActionIntent: Intent? = null
        pictureActionIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pictureActionIntent, 0)
    }

    fun fileFromGallery(uri: Uri){
        val filePath =
            arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor = contentResolver?.query(uri, filePath,
            null, null, null)!!
        c.moveToFirst()
        val columnIndex = c.getColumnIndex(filePath[0])
        path = c.getString(columnIndex)
        c.close()

        var bitmap = BitmapFactory.decodeFile(path) // load
        mBitmap = bitmap
        bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false)

        detail_ivLogo.setImageBitmap(bitmap)

        detail_btnAddText.visibility = View.GONE
        detail_btnAddLogo.visibility = View.GONE
        detail_btnSimpan.visibility = View.VISIBLE
        detail_btnBatal.visibility = View.VISIBLE
    }

    fun saveImageToGallery() {
        MediaStore.Images.Media.insertImage(contentResolver, mBitmap, "" , "");
        editor.putString(meme.id+"_path",path)
        editor.apply()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK){
            data?.data?.let { fileFromGallery(it) }
        }
    }

    fun showAddText(){
        detail_tvText.visibility = View.GONE
        detail_etText.visibility = View.VISIBLE
        detail_etText.isFocusable = true
        detail_btnAddText.visibility = View.GONE
        detail_btnAddLogo.visibility = View.GONE
        detail_btnSimpan.visibility = View.VISIBLE
        detail_btnBatal.visibility = View.VISIBLE
    }

    fun saveText(){
        val text = detail_etText.text.toString()
        editor.putString(meme.id+"_text",text)
        editor.apply()
    }


    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.detail_btnAddLogo->{
                checkPermission()
            }
            R.id.detail_btnAddText->{
                showAddText()
            }
            R.id.detail_btnSimpan->{
                if (mBitmap != null){
                    saveImageToGallery()
                }
                saveText()
                Toast.makeText(this,"Data disimpan",Toast.LENGTH_SHORT).show()
            }
            R.id.detail_btnBatal->{
                detail_ivLogo.setImageResource(0)
                detail_tvText.visibility = View.VISIBLE
                detail_etText.visibility = View.GONE
                detail_etText.isFocusable = false
                detail_btnAddText.visibility = View.VISIBLE
                detail_btnAddLogo.visibility = View.VISIBLE
                detail_btnSimpan.visibility = View.GONE
                detail_btnBatal.visibility = View.GONE
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }
}