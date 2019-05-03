package com.example.ankienen

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*


fun sendMeaning(view: android.view.View, entry: Entry) {
    Snackbar.make(view, entry.meaning, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    // TODO: call addCard()
}

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        inflateButtons()
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

    private fun inflateButtons() {
        val adapter = MyAdapter(emptyArray())
        button_list.adapter = adapter
        button_list.layoutManager = LinearLayoutManager(this)

        requestMeaningAsync(
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
