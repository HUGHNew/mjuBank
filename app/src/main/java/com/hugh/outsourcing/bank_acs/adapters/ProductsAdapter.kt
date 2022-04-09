package com.hugh.outsourcing.bank_acs.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hugh.outsourcing.bank_acs.DetailActivity
import com.hugh.outsourcing.bank_acs.MainActivity
import com.hugh.outsourcing.bank_acs.R
import com.hugh.outsourcing.bank_acs.service.Product

class ProductsAdapter(private val token:String,var items:List<Product>):
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>(){
        inner  class ViewHolder(view: View):RecyclerView.ViewHolder(view){
            val title : TextView = view.findViewById(R.id.title)
            val rate : TextView = view.findViewById(R.id.rate)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_item_product,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.rate.text = (item.annualRate/100.0).toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java).apply {
                putExtra("type",0) // product
                putExtra("product",item)
                putExtra("token",token)
            }
//            it.context.startActivity(intent)
            val act = it.context as MainActivity
            act.coster.launch(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}