package com.example.ankienen

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*


fun sendMeaning(view: android.view.View, entry: Entry) {
    Snackbar.make(view, entry.meaning, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    // TODO: call addCard()
}

class MyAdapter(private val entries: Array<Entry>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(val buttonView: android.widget.Button) : RecyclerView.ViewHolder(buttonView)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        val buttonView = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_component, parent, false) as android.widget.Button
        buttonView.setOnClickListener {
            view -> sendMeaning(view, view.tag as Entry)
        }
        return MyViewHolder(buttonView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.buttonView.text = entries[position].meaning
        holder.buttonView.tag = entries[position]
    }

    override fun getItemCount() = entries.size
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

        val entries = arrayOf(Entry("word", "meaning"))
        inflateButtons(entries)
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

    private fun inflateButtons(entries: Array<Entry>) {
        button_list.adapter = MyAdapter(entries)
        button_list.layoutManager = LinearLayoutManager(this)
    }
}
