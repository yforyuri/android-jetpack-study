package kr.go.yforyuri.jetpackstudy

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindigAdapter {

    @JvmStatic
    @BindingAdapter("colorChange")
    fun colorChange(textView: TextView, number: Int) {
        textView.setTextColor(
            when(number){
                in 0..4 -> Color.BLACK
                in 5..10 -> Color.MAGENTA
                in 11..15 -> Color.BLUE

            else -> Color.YELLOW
        })
    }

    @JvmStatic
    @BindingAdapter("visibleNumber")
    fun visibleNumber(textView2: TextView, number: Int) {
        textView2.visibility = if(number > 5) View.VISIBLE else View.GONE
    }
}