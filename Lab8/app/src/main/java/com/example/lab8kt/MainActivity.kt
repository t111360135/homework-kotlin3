package com.example.lab8kt

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MyAdapter
    private val contacts = ArrayList<Contact>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data:
    Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                val name = it.getString("name") ?: return@let
                val photo = it.getString("phone") ?: return@let
                contacts.add(Contact(name, photo))  //新增資料
                adapter.notifyDataSetChanged()  //更新
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val btn_add = findViewById<Button>(R.id.btn_add)
        val linearLayoutManager = LinearLayoutManager(this)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        adapter = MyAdapter(contacts)
        recyclerView.adapter = adapter

        btn_add.setOnClickListener {
            startActivityForResult(Intent(this, SecActivity::class.java), 1)
        }
    }
}

data class Contact (
    val name: String,
    val phone: String
)