package com.example.readfromfile.model

class LatvianSoundsRepository(private val fileContent: String) {
    // Function that splits sounds as text, and returns split sounds as text
    private fun splitSounds(): ArrayList<String> {
        val allSounds = ArrayList<String>()
        var newSoundL = ""
        for (i in 0..fileContent.length - 1) {
            val letter: Char = fileContent.get(i)
            if (letter == '\n') {
                allSounds.add(newSoundL)
                newSoundL = ""
            } else if (letter != ' ' && letter != '"') {
                newSoundL += letter
            }
        }
        allSounds.add(newSoundL)
        return allSounds
    }

    // Function that from every sound as text makes object LatvianSound, and returns list with all Latvian sounds
    public fun makeSoundList(): ArrayList<LatvianSound> {
        val allSounds = splitSounds()
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
        return allSoundsList
    }
}
