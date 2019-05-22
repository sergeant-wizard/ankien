package com.example.ankienen

import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.json.responseJson
import org.json.JSONArray
import org.json.JSONObject


fun requestMeaning(word: String): Request {
    val key = "your key"
    val url = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/$word"
    return url.httpGet(listOf(Pair("key", key)))
}

fun validateWord(word: String): Boolean {
    if (word.contains(" ")) {
        android.util.Log.e("ankien", "idiom not supported")
        return false
    }
    return true
}

fun parseJson(json: JSONArray): Array<String> {
    var ret = emptyArray<String>()
    for (i in 0 until json.length()) {
        var definitions: JSONArray
        try {
            definitions = json.getJSONObject(i).getJSONArray(("shortdef"))
        } catch (e: org.json.JSONException) {
            return ret
        }
        for (j in 0 until definitions.length()) {
            ret += definitions.getString(j)
        }
    }
    return ret
}

fun requestMeaningAsync(word: String, onSuccess: (Array<Entry>) -> Unit, onFailure: (String) -> Unit) {
    if (!validateWord((word))) {
        // TODO: error handling
        android.util.Log.e("ankien", "invalid word")
        return
    }
    requestMeaning(word).responseJson {
            request, response, result ->
        if (!response.isSuccessful) {
            onFailure(response.responseMessage)
            return@responseJson
        }
        if (result.get().content == "Key is required.") {
            onFailure("Invalid Key")
            return@responseJson
        }
        val stringMeanings = parseJson(result.get().array())
        onSuccess(stringMeanings.map {
            Entry(word, it)
        }.toTypedArray())
    }
}