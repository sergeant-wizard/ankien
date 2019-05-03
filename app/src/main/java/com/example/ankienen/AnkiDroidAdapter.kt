package com.example.ankienen

import com.ichi2.anki.api.AddContentApi

fun defaultDeckId(api: AddContentApi): Long? {
    for (pair in api.deckList) {
        if (pair.value == "Default") {
            return pair.key
        }
    }
    return null
}

fun defaultModel(api: AddContentApi): Long? {
    for (pair in api.modelList) {
        if (pair.value == "Basic") {
            return pair.key
        }
    }
    return null
}

fun addCard(context: android.content.Context, entry: Entry) {
    val api = AddContentApi(context.applicationContext)
    val deckId = defaultDeckId(api)
    if (deckId == null) {
        android.widget.Toast.makeText(context,"default deck not found", android.widget.Toast.LENGTH_SHORT).show()
        return
    }
    val modelId = defaultModel(api)
    if (modelId == null) {
        android.widget.Toast.makeText(context,"model deck not found", android.widget.Toast.LENGTH_SHORT).show()
        return
    }
    api.addNote(modelId, deckId, arrayOf(entry.word, entry.meaning), setOf())
}