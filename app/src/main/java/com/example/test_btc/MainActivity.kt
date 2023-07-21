package com.example.test_btc

import android.app.PendingIntent.getActivity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test_btc.entity.historyEntity
import kotlinx.coroutines.Dispatchers.Main
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val okHttpClient: OkHttpClient = OkHttpClient()
    private var progressDialog: ProgressDialog? = null
    private var tvTime: TextView? = null
    private var tvUsd: TextView? = null
    private var tvUsdOld: TextView? = null
    private var tvEur: TextView? = null
    private var tvEurOld: TextView? = null
    private var tvGbp: TextView? = null
    private var tvGbpOld: TextView? = null
    var mydb: myDBClass? = null
    private var btnHistory: Button? = null
    private var btnConvert: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mydb = myDBClass(this)
        tvTime = findViewById(R.id.tvTime);
        tvUsd = findViewById(R.id.tvUsd);
        tvUsdOld = findViewById(R.id.tvUsdOld);
        tvEur = findViewById(R.id.tvEur);
        tvEurOld = findViewById(R.id.tvEurOld);
        tvGbp = findViewById(R.id.tvGbp);
        tvGbpOld = findViewById(R.id.tvGbpOld);
        btnHistory = findViewById(R.id.btnHistory);
        btnConvert = findViewById(R.id.btnConvert);

        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("BPI Loading")
        progressDialog!!.setMessage("Wait ...")
        load()

        btnHistory!!.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        btnConvert!!.setOnClickListener {
            val intent = Intent(this, ConvertActivity::class.java)
            startActivity(intent)
        }

    }

    private fun countDown(){
        val timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                load()
            }
        }
        timer.start()
    }



    private fun load() {
        val request = Request.Builder()
            .url(BPI_ENDPOINT)
            .build()
        progressDialog!!.show()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Toast.makeText(
                    this@MainActivity, "Error during BPI loading : "
                            + e.message, Toast.LENGTH_SHORT
                ).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = response.body()!!.string()
                runOnUiThread {
                    progressDialog!!.dismiss()
                    parseBpiResponse(body)
                    countDown()

                }
            }
        })
    }

    private fun parseBpiResponse(body: String) {
        try {
            val builderTime = StringBuilder()
            val jsonObject = JSONObject(body)
            val bpiObject = jsonObject.getJSONObject("bpi")
            val timeObject = jsonObject.getJSONObject("time")
            builderTime.append(timeObject.getString("updated")).append("\n\n")
            var a = builderTime.toString()
            tvTime!!.text = a

            val builderUSD = StringBuilder()
            val usdObject = bpiObject.getJSONObject("USD")
            builderUSD.append(usdObject.getString("rate")).append("$").append("\n")
            var b = builderUSD.toString()
            tvUsd!!.text = b

            val builderGBP = StringBuilder()
            val gbpObject = bpiObject.getJSONObject("GBP")
            builderGBP.append(gbpObject.getString("rate")).append("£").append("\n")
            var c = builderGBP.toString()
            tvGbp!!.text = c

            val builderEUR = StringBuilder()
            val euroObject = bpiObject.getJSONObject("EUR")
            builderEUR.append(euroObject.getString("rate")).append("€").append("\n")
            var d = builderEUR.toString()
            tvEur!!.text = d

                mydb!!.addSelected(
                    tvTime!!.text.toString(),
                    tvUsd!!.text.toString(),
                    tvGbp!!.text.toString(),
                    tvEur!!.text.toString(),
                )

        } catch (e: Exception) {
        }
    }

    companion object {
        const val BPI_ENDPOINT = "https://api.coindesk.com/v1/bpi/currentprice.json"
    }
}