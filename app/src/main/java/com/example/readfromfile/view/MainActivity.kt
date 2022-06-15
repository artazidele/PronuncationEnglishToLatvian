package com.example.readfromfile.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.readfromfile.R
import com.example.readfromfile.viewmodel.LatvianSoundsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var areTv: TextView
    private lateinit var areNotTv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Pronunciation English to Latvian")
        areTv = findViewById(R.id.are_tv)
        areNotTv = findViewById(R.id.are_not_tv)
        findViewById<Button>(R.id.find_btn).setOnClickListener {
            val inputText = findViewById<EditText>(R.id.edit_text).text.toString()
            readFiles(inputText)
        }
    }

    // Function that read both files and update text field with word pronuncation, calling function
    private fun readFiles(input: String) {
        val fileEngContent: String = applicationContext.assets.open("eng.txt").bufferedReader().use {
            it.readText()
        }
        val fileLatvianContent: String = applicationContext.assets.open("lv.txt").bufferedReader().use {
            it.readText()
        }
        areTv.text = LatvianSoundsViewModel(fileLatvianContent, fileEngContent, input).getPronuncation().get(0)
        val notExist = LatvianSoundsViewModel(fileLatvianContent, fileEngContent, input).getPronuncation().get(1)
        if (notExist != "") {
            areNotTv.visibility = View.VISIBLE
            areNotTv.text = "Izrunu vārdiem - " + notExist + "- neizdevās atrast."
        } else {
            areNotTv.visibility = View.GONE
        }
    }
}
