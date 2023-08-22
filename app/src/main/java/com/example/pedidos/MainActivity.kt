package com.example.pedidos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var producto : EditText
    lateinit var valor : EditText
    lateinit var add : Button
    lateinit var listView1 : ListView
    lateinit var listView2 : ListView

    var fileHelper = FileHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        producto = findViewById(R.id.editProducto)
        valor = findViewById(R.id.editValor)
        add = findViewById(R.id.button)
        listView1 = findViewById(R.id.list)
        listView2 = findViewById(R.id.list1)

        val (listProducto, listValor) = fileHelper.readData(this)

        var arrayAdapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1,android.R.id.text1, listProducto)
        var arrayAdapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1,android.R.id.text1, listValor)

        listView1.adapter = arrayAdapter1
        listView2.adapter = arrayAdapter2


        add.setOnClickListener {

            val nuevoProducto: String = producto.text.toString()
            listProducto.add(nuevoProducto)
            val valorText = valor.text.toString()
            if (!valorText.isNullOrEmpty()) {
                val nuevoValor: Double = valorText.toDouble()
                listValor.add(nuevoValor)
            } else {
                val nuevoValor: Double = 0.0
                listValor.add(nuevoValor)
            }


            producto.setText("")
            valor.setText("")

            fileHelper.writeData(listProducto, listValor, applicationContext)

            arrayAdapter1.notifyDataSetChanged()
            arrayAdapter2.notifyDataSetChanged()
        }

        listView1.setOnItemClickListener { parent, view, position, id ->
            val elementoAEditar = listProducto[position]

            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.edit_text_layout, null)
            val editText = dialogView.findViewById<EditText>(R.id.editText)

            editText.setText(elementoAEditar)

            dialogBuilder.setView(dialogView)
                .setPositiveButton("Aceptar") { dialog, _ ->
                    val nuevoValorEditado = editText.text.toString()
                    listProducto[position] = nuevoValorEditado
                    arrayAdapter1.notifyDataSetChanged()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }

            val alertDialog = dialogBuilder.create()
            alertDialog.show()
        }

        listView2.setOnItemClickListener { parent, view, position, id ->
            val elementoAEditar = listValor[position]

            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.edit_text_layout, null)
            val editText = dialogView.findViewById<EditText>(R.id.editText)

            editText.setText(elementoAEditar.toString())

            dialogBuilder.setView(dialogView)
                .setPositiveButton("Aceptar") { dialog, _ ->
                    val nuevoValorEditado = editText.text.toString().toDouble()
                    listValor[position] = nuevoValorEditado
                    arrayAdapter2.notifyDataSetChanged()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }

            val alertDialog = dialogBuilder.create()
            alertDialog.show()
        }


    }
}