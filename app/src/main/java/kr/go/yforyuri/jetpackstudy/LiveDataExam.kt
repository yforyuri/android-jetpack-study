package kr.go.yforyuri.jetpackstudy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class LiveDataExam {

    private val number = MutableLiveData<Int>()

    private val numberObserver = Observer<Int> {

    }

    fun init() {
        number.observeForever(numberObserver)
    }

    fun stop() {
        number.removeObserver(numberObserver)
    }
}