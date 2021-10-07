package kr.go.yforyuri.jetpackstudy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap

fun main() {

}

val money = MutableLiveData(0)

/*
val money_str = MutableLiveData("")
fun setMoney() {
    money.observeForever {
        money_str.value = "$it 원"
    }
}
*/

//val money_str = money.map { "$it 원" }

val money_str = money.switchMap { MutableLiveData("") }