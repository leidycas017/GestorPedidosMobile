package com.example.pedidos

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileHelper {
    val FILENAME = "listinfo.dat"

    fun deleteFileIfExists(context: Context) {
        val file = File(context.filesDir, FILENAME)
        if (file.exists()) {
            file.delete()
        }
    }

    fun writeData(listProducto: ArrayList<String>, listValor: ArrayList<Double>, context: Context)
    {

        var fos : FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)

        var oas = ObjectOutputStream(fos)
        oas.writeObject(listProducto)
        oas.writeObject(listValor)
        oas.close()
    }

    fun readData(context: Context): Pair<ArrayList<String>, ArrayList<Double>> {

        val file = File(context.filesDir, FILENAME)
        if (file.exists()) {
            file.delete()
        }

        var listProducto: ArrayList<String> = ArrayList()
        var listValor: ArrayList<Double> = ArrayList()

        try {
            val fis: FileInputStream = context.openFileInput(FILENAME)
            val ois = ObjectInputStream(fis)

            listProducto = ois.readObject() as ArrayList<String>
            listValor = ois.readObject() as ArrayList<Double>

            ois.close()
        } catch (e: FileNotFoundException) {
            listProducto = ArrayList()
            listValor = ArrayList()
        }

        return Pair(listProducto, listValor)
    }

}