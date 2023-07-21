package com.example.test_btc

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_btc.adapter.historyAdapter
import com.example.test_btc.entity.historyEntity
import org.json.JSONException
import java.io.IOException
import kotlin.math.roundToInt


class ConvertActivity : AppCompatActivity() {

    private val adapter by lazy { historyAdapter(ArrayList()) }

    private lateinit var spinner:Spinner

    private var tvvalue: TextView? = null
    private var tvEur: TextView? = null
    var valueCurrency = 0.000000
    private var tvtotal: TextView? = null
    private var edtnumber: EditText? = null
    private var btnConvertBtc: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.convert)

        spinner = findViewById(R.id.currency_spinner);
        tvvalue = findViewById(R.id.tv_value);
        tvtotal = findViewById(R.id.tv_total);
        edtnumber = findViewById(R.id.edt_number);
        btnConvertBtc = findViewById(R.id.btnConvertBtc);

        val currency = resources.getStringArray(R.array.currency_array)

        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, currency)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

            btnConvertBtc!!.setOnClickListener {
                if (edtnumber!!.text.isEmpty()){
                    Toast.makeText(this@ConvertActivity, "Please input value", Toast.LENGTH_LONG).show()
                }else{
                    convertData()
                }
            }

        }


    }
    private fun convertData(){
        when (spinner!!.selectedItem.toString()) {
            "EUR" -> {
                tvvalue!!.text = "1 EUR = 0.000037 BTC"
                valueCurrency = 0.000037.toDouble()

            }
            "GBP" -> {
                tvvalue!!.text = "1 EUR = 0.000043 BTC"
                valueCurrency = 0.000043.toDouble()

            }
            "USD" -> {
                tvvalue!!.text = "1 EUR = 0.000034 BTC"
                valueCurrency = 0.000034.toDouble()
            }
        }
        val total = edtnumber!!.text.toString().toDouble() * valueCurrency
        tvtotal!!.text = total.toString()


    }



}