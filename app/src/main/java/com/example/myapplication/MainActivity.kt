package com.example.myapplication

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var item:EditText
    lateinit var add:Button
    lateinit var listview:ListView
    var itemlist = ArrayList<String>()
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item = findViewById(R.id.item)
        add = findViewById(R.id.add)
        listview = findViewById(R.id.listview)

        itemlist = fileHelper.readata(this)

        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemlist)
        listview.adapter = arrayAdapter
        add.setOnClickListener {
            var itemName:String = item.text.toString()
            itemlist.add(itemName)
            item.setText("")
            fileHelper.writedata(itemlist,applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }

        listview.setOnItemClickListener { adapterView, view, i, l ->
            var alter = AlertDialog.Builder(this)
            alter.setTitle("Delete")
            alter.setMessage("Do you want to delete?")
            alter.setCancelable(false)
            alter.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            alter.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                var position = 0
                itemlist.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writedata(itemlist,applicationContext)
            })
            alter.create().show()
        }
    }
}