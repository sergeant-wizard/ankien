package com.example.ankienen

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*


fun sendMeaning(view: android.view.View, entry: Entry) {
    addCard(view.context, entry)
    // TODO: Error handling
    display(view, "Added")
}

fun display(view: android.view.View, msg: String) {
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show()
}

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        // FIXME: there should be a better layout
        toolbar_layout.isTitleEnabled = false
        inflateButtons(getIntentText(intent))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                inflateButtons(query)
                return true
            }
            override fun onQueryTextChange(query: String): Boolean {
                return false
            }

        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            inflateButtons(getIntentText(intent))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getIntentText(intent: Intent): String {
        return if (intent.type == "text/plain") {
            intent.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            // TODO: add a text box to input words directly
            "pith"
        }
    }

    private fun inflateButtons(word: String) {
        val adapter = MyAdapter(emptyArray())

        button_list.adapter = adapter
        button_list.layoutManager = LinearLayoutManager(this)
        searchView.setQuery(word, false)

        requestMeaningAsync(
            word,
            onSuccess = { meanings ->
                adapter.entries = meanings
                adapter.notifyDataSetChanged()
            },
            onFailure = { msg ->
                // TODO: exception handling
                Log.e("ankien", msg)
            }
        )
    }
}
