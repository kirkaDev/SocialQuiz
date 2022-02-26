package com.desiredsoftware.socialquiz.utils

import android.content.Context
import android.util.Log
import com.iceteck.silicompressorr.SiliCompressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class VideoCompressor constructor(
    val context: Context
) {
        suspend fun compressVideo(videoUriString: String, destinationDirectory: String): String? {
            var compressedFilePath : String? = null
            Log.d("compressVideo", "compressVideo start")
            withContext(Dispatchers.IO) {
                try {
                    val f: File = context.getDir(destinationDirectory, Context.MODE_PRIVATE)
                    if (f.mkdirs() || f.isDirectory){
                        compressedFilePath = SiliCompressor.with(context)
                            .compressVideo(videoUriString, f.absolutePath)
                    }
                    else{
                        Log.e("compressVideo", "compressVideo error: can't create dir")
                    }

                } catch (e: Exception) {
                    Log.e("compressVideo", "compressVideo error: ${e.message}, cause: ${e.cause}")
                }
            }
            Log.d("compressVideo", "compressVideo after await: compressedFilePath=$compressedFilePath")
            return compressedFilePath
        }
}