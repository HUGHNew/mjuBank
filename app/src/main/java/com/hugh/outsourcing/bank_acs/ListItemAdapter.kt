package com.hugh.outsourcing.bank_acs

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListItemAdapter(private val items:List<Pair<String,String>>):
    RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title : TextView = view.findViewById(R.id.title)
        val desc : TextView = view.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item,parent,false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.first
        holder.desc.text = item.second
        holder.itemView.setOnClickListener {
            it.context.startActivity(Intent(it.context,DetailActivity::class.java))
        }
    }

    override fun getItemCount(): Int = items.size
}