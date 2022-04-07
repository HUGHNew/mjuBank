package com.hugh.outsourcing.bank_acs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.hugh.outsourcing.bank_acs.service.Asset
import java.text.SimpleDateFormat
import java.util.*

class AssetsAdapter(var items:List<Asset>):
    RecyclerView.Adapter<AssetsAdapter.ViewHolder>(){
    inner  class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title : TextView = view.findViewById(R.id.title)
        val amount : TextView = view.findViewById(R.id.amount)
        val dueDate:TextView = view.findViewById(R.id.due_date)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_item_asset,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.productName
        holder.amount.text = item.amount.toString()
//        val datetime = "yyyy-MM-dd HH:mm:ss"
//        val formatter = SimpleDateFormat(datetime, Locale.CHINA)
        holder.dueDate.text = item.purchaseDate
        holder.itemView.setOnClickListener {
            // todo dialog for asset
            val ctx = MainActivity.mMainContext!!
            AlertDialog.Builder(holder.itemView.context)
                .setTitle(item.productName)
                .setMessage("购买金额:${item.amount}\n到期时间:${item.purchaseDate}")
                .setPositiveButton("确定"){
                    _,_ ->
                }
                .create()
                .show()
        }
    }

    override fun getItemCount(): Int = items.size
}