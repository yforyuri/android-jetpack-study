package kr.go.yforyuri.jetpackstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.go.yforyuri.jetpackstudy.databinding.ActivityDataBindingBinding

class DataBindingActivity : AppCompatActivity() {

    private val _number = MutableLiveData<Int>().apply {
        this.value = 0
    }
    val number: LiveData<Int>
        get() = _number

    private lateinit var binding: ActivityDataBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_data_binding)

        // view binding으로 초기화
//        binding = ActivityDataBindingBinding.inflate(layoutInflater)

        // data binding으로 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding)
        with(binding) {
            lifecycleOwner = this@DataBindingActivity
            main = this@DataBindingActivity
        }
    }

    fun setText(): String = "공부합시다"

    fun plusNum() {
        _number.value = _number.value!! +1
    }

}