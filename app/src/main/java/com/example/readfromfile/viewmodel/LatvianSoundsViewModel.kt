package com.example.readfromfile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.readfromfile.model.LatvianSoundsRepository

class LatvianSoundsViewModel(
    private val fileLContent: String,
    private val fileEContent: String,
    private val input: String
) : ViewModel() {
    // Function that get pronuncation for all words, and returns it as sentence
    public fun getPronuncation(): String {
        var inputWordList = ArrayList<String>()
        var newWord = ""
        for (i in 0..input.length - 1) {
            var letter: Char = input.get(i)
//            if (letter.isLetter() == false) {
//                var j = i
//                while (letter.isLetter() == false && j < input.length - 1 || (letter == ' ' || letter == '\n')) {
//                    j += 1
//                    letter = input.get(j)
//                }
//            }
            if (letter <= 'Z' && letter >= 'A') {
                val letterNumber = letter.toInt()
                letter = Char(letterNumber + 32)
            }
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
        val alEngWords = ArrayList<String>()
        newWord = ""
        for (i in 0..fileEContent.length - 1) {
            val letter: Char = fileEContent.get(i)
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
                    pronuncation += makePronuncation(line)
                    break
                }
            }
            pronuncation += " "
        }
        return pronuncation
    }

    // Function that makes pronuncation for one word
    private fun makePronuncation(englishWordLine: String): String {
        val latvianSoundList = LatvianSoundsRepository(fileLContent).makeSoundList()
        var pronuncation = ""
        var j = 0
        while (englishWordLine[j] != '[') {
            j += 1
        }
        j += 3
        var s = ""
        var count = 0
        while (j < englishWordLine.length - 1) {
            while (englishWordLine[j] != Char(39)) {
                s += englishWordLine[j]
                j += 1
            }
            for (oneS in latvianSoundList) {
                if (oneS.sound == s) {
                    count += 1
                    pronuncation += oneS.pronuncation
                    break
                }
            }
            if (englishWordLine[j + 1] == ']') {
                break
            }
            s = ""
            j += 3
        }
        return pronuncation
    }
}
