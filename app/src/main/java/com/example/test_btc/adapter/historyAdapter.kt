package com.example.test_btc.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.test_btc.R
import com.example.test_btc.entity.historyEntity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class historyAdapter(private var reportList : ArrayList<historyEntity>) : RecyclerView.Adapter<historyAdapter.ReportViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_history_list,
            parent,false)
        return ReportViewHolder(itemView)
    }


    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val currentItem = reportList[position]

        holder.tvtime.text = currentItem.time
        holder.tvusd.text = currentItem.usd
        holder.tvgbp.text = currentItem.gbp
        holder.tveur.text = currentItem.eur


    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    fun setData(newReportList: ArrayList<historyEntity>){
        reportList = newReportList
        notifyDataSetChanged()
    }


    class ReportViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvtime : TextView = itemView.findViewById(R.id.tv_time)
        val tvusd : TextView = itemView.findViewById(R.id.tv_usd)
        val tvgbp : TextView = itemView.findViewById(R.id.tv_gbp)
        val tveur : TextView = itemView.findViewById(R.id.tv_eur)
    }
}