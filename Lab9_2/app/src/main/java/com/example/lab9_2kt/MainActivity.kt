package com.example.lab9_2kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var btn_calc: Button
    private lateinit var ed_height: EditText
    private lateinit var ed_weight: EditText
    private lateinit var ed_age: EditText
    private lateinit var tv_weight: TextView
    private lateinit var tv_fat: TextView
    private lateinit var tv_bmi: TextView
    private lateinit var tv_progress: TextView
    private lateinit var progressBar2: ProgressBar
    private lateinit var ll_progress: LinearLayout
    private lateinit var btn_boy: RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_calc = findViewById(R.id.btn_calc)
        ed_height = findViewById(R.id.ed_height)
        ed_weight = findViewById(R.id.ed_weight)
        ed_age = findViewById(R.id.ed_age)
        tv_weight = findViewById(R.id.tv_weight)
        tv_fat = findViewById(R.id.tv_fat)
        tv_bmi = findViewById(R.id.tv_bmi)
        tv_progress = findViewById(R.id.tv_progress)
        progressBar2 = findViewById(R.id.progressBar2)
        ll_progress = findViewById(R.id.ll_progress)
        btn_boy = findViewById(R.id.btn_boy)

        btn_calc.setOnClickListener {
            when {
                ed_height.length() < 1 -> showToast("請輸入身高")
                ed_weight.length() < 1 -> showToast("請輸入體重")
                ed_age.length() < 1 -> showToast("請輸入年齡")
                else -> runCoroutines() //執行 runCoroutines 方法
            }
        }
    }
    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    private fun runCoroutines() {
        tv_weight.text = "標準體重\n 無"
        tv_fat.text = "體脂肪\n 無"
        tv_bmi.text = "BMI\n 無"
        progressBar2.progress = 0   //初始化
        tv_progress.text = "0%"
        ll_progress.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Main) {
            var progress = 0
            while (progress < 100) {
                delay(50)   //執行緒延遲50ms
                progressBar2.progress = progress
                tv_progress.text = "$progress%"
                progress++
            }
            ll_progress.visibility = View.GONE
            val height = ed_height.text.toString().toDouble()
            val weight = ed_weight.text.toString().toDouble()
            val age = ed_age.text.toString().toDouble()
            val bmi = weight / ((height / 100).pow(2))
            val (stand_weight, body_fat) = if (btn_boy.isChecked) {    //計算男女體脂率
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }
            tv_weight.text = "標準體重 \n${String.format("%.2f", stand_weight)}"
            tv_fat.text = "體脂肪 \n${String.format("%.2f", body_fat)}"
            tv_bmi.text = "BMI \n${String.format("%.2f", bmi)}"
        }
    }
}