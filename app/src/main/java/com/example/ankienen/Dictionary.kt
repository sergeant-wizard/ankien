package com.example.ankienen

import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.json.responseJson


fun requestMeaning(): Request {
    val key = null  // your key
    val url = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/voluminous"
    return url.httpGet(listOf(Pair("key", key)))
}

fun requestMeaningAsync(callback: (Array<Entry>) -> Unit) {
    requestMeaning().responseJson {
            request, response, result ->
        // TODO: error handling
        val defJson = result.get().array().getJSONObject(0).getJSONArray("shortdef")
        val numMeanings = defJson.length()
        val meanings = Array(numMeanings) {idx ->
            Entry("word", defJson.getString(idx))
        }
        callback(meanings)
    }
}