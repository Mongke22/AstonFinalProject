package com.example.astonfinalproject.presentation.recyclerView.holders

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.astonfinalproject.R
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CharacterViewHolder(
    val view: View,
    var characterSavePictureFunc: ((Int, String) -> Unit)? = null,
) : RecyclerView.ViewHolder(view) {
    private var name: TextView = view.findViewById(R.id.etName)
    private var gender: ImageView = view.findViewById(R.id.ivTopLeftIcon)
    private var species: ImageView = view.findViewById(R.id.ivTopRightIcon)
    private var status: ImageView = view.findViewById(R.id.ivBottomRightIcon)
    private var image: ImageView = view.findViewById(R.id.imageViewCharacterPicture)

    fun setName(theName: String) {
        name.text = theName
    }

    fun setGender(theGender: String) {
        gender.setImageResource(
            getGenderImageByString(theGender)
        )
    }

    fun setSpecies(theSpecies: String) {
        species.setImageResource(
            getSpeciesImageByString(theSpecies)
        )
    }


    fun setStatus(theStatus: String) {
        status.setImageResource(
            getStatusImageByString(theStatus)
        )
    }

    fun setImage(imageSource: String, imageUrl: String, id: Int){
        loadImage(imageSource, imageUrl, id)
    }


    private fun getGenderImageByString(theGender: String): Int {
        return when (theGender) {
            "male" -> R.drawable.ic_male
            "female" -> R.drawable.ic_female
            else -> R.drawable.ic_unknown
        }
    }

    private fun getSpeciesImageByString(theSpecies: String): Int {
        return when (theSpecies) {
            "human" -> R.drawable.ic_human
            "alien" -> R.drawable.ic_alien
            else -> R.drawable.ic_unknown
        }
    }

    private fun getStatusImageByString(theStatus: String): Int {
        return when (theStatus) {
            "alive" -> R.drawable.ic_alive
            "dead" -> R.drawable.ic_dead
            else -> R.drawable.ic_unknown
        }
    }

    private fun loadImage(src: String, url: String, id: Int) {
        if (url.isEmpty()) {
            image.setImageResource(R.drawable.default_picture)
            return
        }
        if(src != "unknown"){
            image.setImageURI(Uri.parse(src))
            return
        }
        val uiHandler = Handler(Looper.getMainLooper())
        uiHandler.post {
            Picasso.with(view.context)
                .load(url)
                .into(object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                        image.setImageBitmap(bitmap)
                        val path = saveImageToPhone(bitmap, "character$id")
                        characterSavePictureFunc?.invoke(id, path)
                    }

                    override fun onBitmapFailed(errorDrawable: Drawable?) {
                        if(src.isEmpty()){
                            image.setImageResource(R.drawable.default_picture)
                        }else{
                            image.setImageURI(Uri.parse(src))
                        }
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                })
        }


    }

    private fun saveImageToPhone(image: Bitmap, fileName: String): String {
        val imagesFolder = File(view.context.cacheDir, "images")
        var path = ""
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, fileName)
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            path = file.absolutePath
        } catch (e: IOException) {
            Log.i("IOException", "${e.message}")
        }
        return path
    }
}