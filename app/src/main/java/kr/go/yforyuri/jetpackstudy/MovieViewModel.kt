package com.example.paging3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.paging3.network.MovieInterface
import com.example.paging3.util.PAGE_SIZE

class MovieViewModel: ViewModel() {

    private val movieInterface = MovieInterface.invoke()

    private val s = MutableLiveData("")

    /*
    * LiveData Formation: 라이브뎅이터의 데이터를 새로 가공하거나 이용하여 새로운 라이브 데이터를 만들 수 있다.
    * map(), switchMap()
    */

    /*
    * Pager: pagingSource를 실행하기 위한 반응형 스트림(데이터 흐름, 연결 통로) -> Pager객체를 PagingSource에서 load() 호출
    * Pager(PagingConfig()) {PagingSource}.리턴자료형.cachedIn(코루틴)
    * 리턴자료형 -> 1.livedata 2.flow
    * 1. livedata  -> Observe
    * 2. flow -> collectLatest
    * cachedIn -> 이미 로드된 데이터를 저장
    */

    /*
    * PagingConfig
    * 1. pagesize : load()에서 한번에 로딩할 아이템의 개수, 반드시 설정해줘야 함
    * 2. initialLoadSize: PagingSource에서 맨 처음에 로딩할 아이템 개수, 기본값 = pageSize*3
    * 3. preferenceDistance: 다음 데이터를 로드하기 위해 미리 로드해야할 아이템 개수, 기본값 = pageSize
    * 4. maxSize : 페이지 삭제 전 PagingSource에서 최대로 로드할 수 있는 아이템 개수
    *    -> 기본값 : 페이지가 삭제되지 않음.
    *    -> 로드된 페이지가 maxSize보다 많을 경우 삭제되기 시작, 삭제함으로서 메모리 공간을 관리할 수 있
    */
    val list = s.switchMap {
        Pager(PagingConfig(pageSize = PAGE_SIZE, maxSize = 200)) {
            MoviePagingSource(movieInterface, it)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(word: String) {
        s.value = word
    }
}