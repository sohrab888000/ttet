package com.draco.ludere.assets

import android.content.Context
import com.draco.ludere.R
import java.io.File

class PrivateData(context: Context) {
    /* Prefer external storage, fall back on internal storage */
    private val storagePath: String = (context.getExternalFilesDir(null) ?: context.filesDir).path
    val romBytes = context.resources.openRawResource(R.raw.rom).use { it.readBytes() }
    val sram = File("$storagePath/sram")
    val state = File("$storagePath/state")
    val tempState = File("$storagePath/tempstate")
}