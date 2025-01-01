package com.gravity.billeasy.ui_layer.data_import_export

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Stable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonWriter
import com.gravity.billeasy.data_layer.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Calendar


@Stable
class ImportAndExportData(var context: Context) {
    private var gSon: Gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()

    private fun getFileName(): String {
        // TODO once seperate DB is created for shop we need to store the name of the shop in pref and get the shop name here
        return "Sk stores products ${Calendar.getInstance().timeInMillis}.json"
    }

    fun writeTextToFile(products: List<Product>, coroutineScope: CoroutineScope) {
        coroutineScope.launch(Dispatchers.IO) {
            val dir = File("//sdcard//Download//")
            val myExternalFile = File(dir, getFileName())
            try {
                JsonWriter(FileWriter(myExternalFile)).use { writer ->
                    gSon.toJson(products, List::class.java, writer)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "File downloaded successfully as ${myExternalFile.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    suspend fun importFile(uri: Uri, context: Context): List<Product>? {
        var inputStream: InputStream? = null
        try {
            if (validateFile(uri, context)) {
                inputStream = context.contentResolver.openInputStream(uri)
                val type = object : TypeToken<List<Product>>() {}.type
                val products = gSon?.fromJson<List<Product>>(
                    InputStreamReader(inputStream), type
                )
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Import completed", Toast.LENGTH_LONG).show()
                }
                return products
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private suspend fun validateFile(uri: Uri, context: Context): Boolean {
        val fileName = context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        } ?: throw Exception("Unable to get file name")

        if (!fileName.endsWith(".json", ignoreCase = true)) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Only files with .json extension are supported",
                    Toast.LENGTH_LONG
                ).show()
            }
            return false
        }
        return true
    }
}
