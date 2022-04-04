package com.hugh.outsourcing.bank_acs

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hugh.outsourcing.bank_acs.service.Product

class ProductsAdapter(private val token:String,private val items:List<Product>):
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>(){
        inner  class ViewHolder(view: View):RecyclerView.ViewHolder(view){
            val title : TextView = view.findViewById(R.id.title)
            val desc : TextView = view.findViewById(R.id.desc)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.desc.text = "年利率: ${item.dailyAmount/100.0}%"
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context,DetailActivity::class.java).apply {
                putExtra("type",0) // product
                putExtra("product",item)
                putExtra("token",token)
            }
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}