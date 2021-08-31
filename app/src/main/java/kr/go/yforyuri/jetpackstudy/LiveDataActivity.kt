package kr.go.yforyuri.jetpackstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class LiveDataActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var button: Button

    private val number = MutableLiveData<Int>().also {
        it.value =0
    }
    private val numberObserver = Observer<Int> {
        textView.text = "$it"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)
        textView = findViewById(R.id.text)
        button = findViewById(R.id.button)

//        number.value = 0
        button.setOnClickListener {
            plusNumber()
        }
//        number.observe(this, {
//            textView.text ="$it"
//        })
        number.observe(this, numberObserver)
    }

    private fun plusNumber() {
        val data = number.value
        number.value = data!! + 1
    }

    override fun onPause() {
        super.onPause()
        number.removeObserver(numberObserver)
    }

    override fun onResume() {
        super.onResume()
        number.observe(this, numberObserver)
    }
}