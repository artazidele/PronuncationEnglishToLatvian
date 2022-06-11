package com.example.readfromfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var areTv: TextView
    private lateinit var areNotTv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        areTv = findViewById(R.id.are_tv)
        areNotTv = findViewById(R.id.are_not_tv)
        findViewById<Button>(R.id.find_btn).setOnClickListener {
            val inputText = findViewById<EditText>(R.id.edit_text).text.toString()
            readFile(inputText)
        }
    }

    private fun readFile(input: String) {
        var inputWordList = ArrayList<String>()
        var newWord = ""
        for (i in 0..input.length - 1) {
            val letter: Char = input.get(i)
            if (letter == ' ' || letter == '\n') {
                inputWordList.add(newWord)
                newWord = ""
            } else {
                newWord += letter
            }
        }
        if (newWord != "") {
            inputWordList.add(newWord)
        }

        val fileEngContent: String = applicationContext.assets.open("eng.txt").bufferedReader().use {
            it.readText()
        }
        val alEngWords = ArrayList<String>()
        newWord = ""
        for (i in 0..fileEngContent.length - 1) {
            val letter: Char = fileEngContent.get(i)
            if (letter == '\n') {
                alEngWords.add(newWord)
                newWord = ""
            } else if (letter != ' ' && letter != '"') {
                newWord += letter
            }
        }
        alEngWords.add(newWord)


        var pronuncation = ""
        for (inputWord in inputWordList) {
            for (line in alEngWords) {
                if (line.startsWith(inputWord + ":")) {
                    pronuncation += getPronuncation(line)
                    break
                }
            }
            pronuncation += " "
        }
        areTv.text = pronuncation
    }

    private fun getPronuncation(engLine: String): String {
        var pronuncation = "" //engLine

        val fileEngContent: String = applicationContext.assets.open("lv.txt").bufferedReader().use {
            it.readText()
        }
        val allSounds = ArrayList<String>()
        var newSoundL = ""
        for (i in 0..fileEngContent.length - 1) {
            val letter: Char = fileEngContent.get(i)
            if (letter == '\n') {
                allSounds.add(newSoundL)
                newSoundL = ""
            } else if (letter != ' ' && letter != '"') {
                newSoundL += letter
            }
        }
        allSounds.add(newSoundL)
//        pronuncation = allSounds.size.toString()

        val allSoundsList = ArrayList<LatvianSound>()
        for (i in allSounds) {
            var sound = ""
            var index = 0
            while (i[index] != ':') {
                sound += i[index]
                index += 1
            }
            var pr = ""
            index += 2
            while (i[index] != ',') {
                pr += i[index]
                index += 1
            }
            index += 1
            var prC = ""
            while (i[index] != ']') {
                prC += i[index]
                index += 1
            }
            val newSound = LatvianSound(sound, pr, prC)
            allSoundsList.add(newSound)
        }

//        for (sou in allSoundsList) {
//            pronuncation += sou.sound + " : "
//        }
//        pronuncation = allSoundsList.size.toString()
        var j = 0
        while (engLine[j] != '[') {
            j += 1
        }
        j += 3
        var s = ""
        var count = 0
        while (j < engLine.length - 1) {

//            s += engLine[j]
//            j += 1
            while (engLine[j] != Char(39)) {
                s += engLine[j]
                j += 1
            }
            for (oneS in allSoundsList) {
                if (oneS.sound == s) {
                    count += 1
                    pronuncation += oneS.pronuncation
//                    break
                }
            }
            if (engLine[j + 1] == ']') {
                break
            }
            s = ""
            j += 3
        }


        areNotTv.text = count.toString()



        return pronuncation
    }
}
