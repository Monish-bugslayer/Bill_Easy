package com.gravity.billeasy.ui_layer.data_import_export

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gravity.billeasy.data_layer.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Calendar


@Stable
class ImportAndExportData(var context: Context) {
    private var gSon: Gson? = GsonBuilder().serializeNulls().setPrettyPrinting().create()

    fun convertClassToJson(product: List<Product>): String? {
        val allData = ExportProduct(product)
        return gSon?.toJson(allData)
    }

    private fun getFileName(): String {
        // TODO once seperate DB is created for shop we need to store the name of the shop in pref and get the shop name here
        return "Sk stores products ${Calendar.getInstance().timeInMillis}.json"
    }

    fun writeTextToFile(jsonResponse: String?, coroutineScope: CoroutineScope) {
        coroutineScope.launch(Dispatchers.IO) {
            if (jsonResponse != "") {
                val dir = File("//sdcard//Download//")
                val myExternalFile = File(dir, getFileName())
                var fos: FileOutputStream? = null
                try {
                    fos = FileOutputStream(myExternalFile)
                    fos.write(jsonResponse?.toByteArray())
                    fos.close()
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
    }

//    @Composable
//    fun OpenFileManager() {
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        intent.type = "*/*"
//        GetDataFromFile()
//    }

    suspend fun importFile (uri: Uri): String {
        var inputStream: InputStream? = null
        val stringBuilder = StringBuilder()
        try {
            inputStream = context.contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line = reader.readLine()
            while (line != null) {
                stringBuilder.append(line).append('\n')
                line = reader.readLine()
            }
        } catch (e: IOException) { e.printStackTrace() }
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Import completed", Toast.LENGTH_LONG).show()
        }
        return stringBuilder.toString()
    }
}
