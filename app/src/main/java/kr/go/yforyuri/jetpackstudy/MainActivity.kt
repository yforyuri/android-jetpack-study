package kr.go.yforyuri.jetpackstudy

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kr.go.yforyuri.jetpackstudy.data.ApiService
import kr.go.yforyuri.jetpackstudy.data.MovieRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val textView:TextView by lazy { findViewById(R.id.textView) }
    private val compositionDispoable by lazy { CompositeDisposable() }
    private val scope by lazy { CoroutineScope(Dispatchers.IO) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service: ApiService = ApiService.invoke()

/*
        //  비동기 요청
        service.getPopularMovie(1).enqueue(object : Callback<MovieRes> {
            override fun onResponse(call: Call<MovieRes>, response: Response<MovieRes>) {
                textView.text = response.body().toString()
            }

            override fun onFailure(call: Call<MovieRes>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
*/

/*
        // 동기 요청
        CoroutineScope(Dispatchers.IO).launch {
            val result:Response<MovieRes> = service.getPopularMovie(1).execute()
            runOnUiThread {
                textView.text = result.body().toString()
            }
        }*/

/*
        //RxJava2
        compositionDispoable.add(
            service.getPopularMovie(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    // 요청성공
                    textView.text = it.results[0].toString()
                }, {
                    // 요청 실패
                    Log.e(TAG, "onFailure: ${it.message}", )
                })
        )
        */

//        Coroutine
        scope.launch {
            try {
                val result: MovieRes = service.getPopularMovie(1).body()!!
                runOnUiThread {
                    textView.text = result.results[0].toString()
                }
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: ${e.message}" )
            }

//            launch(Dispatchers.Main) {
//                ui에서 처리해주는 것이 아닐 때
//            }
            
        }

        /*
        Coroutine scope - 코루틴이 실행되는 범위
        1. CoroutineScope 가장 기본적인 코루틴 / cancel로 종료할 수 있음
        2. LifecycleScope 라이프 사이클이 있어서 라이프 사이클에 맞게 일하는 코루틴
        3. viewModelScope 뷰모델의 라이프사이클에 맞게 일하는 코루틴, 뷰모델이 clear될 때 자동으로 종료됨
        4. GlobalScope 전체 앱에서 일하는 코루틴. 안드로이드 앱에서 사용하지 않는 것을 권장/ 라이프 사이클을 함부로 종료할 수 없어서 많은 메모리 누수 발생

        Coroutine context -코루틴이 실행되는/일하는 장소
        1. Dispatchers.IO - network, 내부DB와 같은 백그라운드 스레드
        2. Dispatchers.Main - 메인스레드
        3. Dispatchers.Default - 엄청 많은 리스트 등 cpu 부하가 많은 작업들을 할 때 쓰면 좋은 스레드
        4. Dispatchers.Unconfinded - 현재 스레드, global scope와 함께 사용 /안드로이드 앱에서 사용하지 않는것이 권장됨

        Coroutine 사용하기
        CoroutineScope(CoroutineContext).launch {
        실행내용
        }
        */

    }

    override fun onDestroy() {
        super.onDestroy()
        compositionDispoable.dispose()
        scope.cancel()
    }
}