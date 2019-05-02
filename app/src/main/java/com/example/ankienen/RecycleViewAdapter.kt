package com.example.ankienen

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class MyAdapter(var entries: Array<Entry>) :
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