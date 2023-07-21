package com.example.test_btc

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_btc.adapter.historyAdapter
import com.example.test_btc.entity.historyEntity
import org.json.JSONException
import java.io.IOException


class HistoryActivity : AppCompatActivity() {
    var mydb: myDBClass? = null
    private val adapter by lazy { historyAdapter(ArrayList()) }
    var arrlist = ArrayList<historyEntity>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history)

        recyclerView = findViewById(R.id.rcv_list_report_admin);
        recyclerView.adapter = adapter
        setupRecycleView()
        mydb = myDBClass(this)

        viewAllData()
    }
    private fun viewAllData() {

            val response= mydb!!.readCourses()
            arrlist = response
            adapter.setData(arrlist)
    }
    private fun setupRecycleView(){
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView.setHasFixedSize(true)
    }


}