package kr.go.yforyuri.jetpackstudy

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.go.yforyuri.jetpackstudy.data.Movie
import kr.go.yforyuri.jetpackstudy.network.MovieInterface
import kr.go.yforyuri.jetpackstudy.util.PAGE_SIZE
import kr.go.yforyuri.jetpackstudy.util.START_PAGE_INDEX

import java.lang.Exception

// paging source class : 페이징 데이터 소스를 정릐하기 위한 클래스 <page(key),data>()

class MoviePagingSource(val movieInterface: MovieInterface, val s: String): PagingSource<Int, Movie>() {

    /*
    * getRefreshKey() : 리프레시 또는 재 로드 요청을 할 떄 이전 positionㅇ에서 중단된 page(key)값을 가져오는 함수
    * state.anchorPosition : 최근 position
    * prevKey == null -> 첫페이지
    * nextKey == null -> 마지막펭이지
    * prevKey == null && nextKey == null -> 페이지가 딱 하나만 존재할 때
    */
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    /*
    * load() : 페이징 데이터를 가져오는 함수: 페이지가 로딩ㅇ될 때 실행
    * LoadResult<page, data>
    * data : 리사이클러뷰에 전달할 데이터(페이징으로 로드된 데이터)
    * prevKey : 이 전 페이지
    * nextKey : 다음 페이지
    */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        // page : 데이터를 로딩해올 페이지 값. param.key==null이면 첫째 페이지이므로 START_PAGE_INDEX 를 리턴해야한다
        val page = params.key ?: START_PAGE_INDEX
        return try {
            val result = movieInterface.getAllMovies(s, page).body()!!
            val data: List<Movie> = result.search //리사이클러뷰에 전달할 데이터 리스트
            val last = result.totalResults.toInt()
            LoadResult.Page(
                data,
                prevKey = if(page == START_PAGE_INDEX ) null else page-1,
                nextKey = if(0 >= last + page * PAGE_SIZE) null else page+1
            )
//            nextKey = if(data.isEmpty()) null else page+1 -> 마지막페이지인지 알 수 있는 방법이 없을 때
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}