package com.example.paging3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3.network.Movie
import kr.go.yforyuri.jetpackstudy.databinding.ViewHolderMovieBinding

// 사용자가 로드된 데이터의 끝까지 스크롤 할 때 리사이클러뷰 어댑터가 자동으로 데이토를 요청함
// submitData() 함수로 PagingSource의 뎅이터를 전달받는다.
class MoviePageAdapter: PagingDataAdapter<Movie, MoviePageAdapter.MyViewHolder>(DIFF_UTIL) {

    override fun onBindViewHolder(holder: MoviePageAdapter.MyViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.setBind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePageAdapter.MyViewHolder {
        //리사이클러뷰 데이터바인딩 객체 초기화
        val binding = ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    inner class MyViewHolder(val binding: ViewHolderMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setBind(item: Movie) {
            with(binding) {
                movie = item
            }
        }
    }

    /*
    * DIFF_UTIL
    *    리스트를 업데이트(newItem, oldItem)하는데 도움을 주는 유틸리티 클래스
    *    서로 다른 아이템인지를 체크하여 달라진 아이템만 갱신함
    *    리사이클러뷰 성능 향상
    * newItem : 새로 추가하거나 갱신해야할 리스트
    * oldItem : 현재 리스트에 노출되고 있는 리스트
    * calculateDiff -> DiffCallback 실행 -> DiffResult -> diffResult.dispatchUpdatesTo(this)
    */

//    private fun calDiff(newTiles: MutableList<Tile>) {
//        val tileDiffUtilCallback = TileDiffUtilCallback(dataSet, newTiles)
//        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(tileDiffUtilCallback)
//        diffResult.dispatchUpdatesTo(this)
//    }

    companion object {
//        DiffUtil.ItemCallback : areItemsTheSame()로 아이템 아이디를 비교하고 areContentsTheSame()으로 한 번 더 비교한다.
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
//    아이템이 가지고 있는 정보로 1차 비교
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }
//    객체가 같은지 서로 비교
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

}