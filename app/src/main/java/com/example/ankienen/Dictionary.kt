package com.example.ankienen

import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.json.responseJson
import org.json.JSONArray




fun requestMeaning(): Request {
    val key = null  // your key
    val url = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/voluminous"
    return url.httpGet(listOf(Pair("key", key)))
}

fun requestMeaningAsync(onSuccess: (Array<Entry>) -> Unit, onFailure: (String) -> Unit) {
    requestMeaning().responseJson {
            request, response, result ->
        if (!response.isSuccessful) {
            onFailure(response.responseMessage)
            return@responseJson
        }
        if (result.get().content == "Key is required.") {
            onFailure("Invalid Key")
            return@responseJson
        }
        val defJson = result.get().array().getJSONObject(0).getJSONArray("shortdef")
        val numMeanings = defJson.length()
        val meanings = Array(numMeanings) {idx ->
            Entry("word", defJson.getString(idx))
        }
        onSuccess(meanings)
    }
}