package kr.go.yforyuri.jetpackstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.paging.filter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paging3.MoviePageAdapter
import com.example.paging3.MovieViewModel
import kr.go.yforyuri.jetpackstudy.databinding.ActivityPaging3Binding

class Paging3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityPaging3Binding
    private val viewModel: MovieViewModel by viewModels()
    private val movieAdapter = MoviePageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_paging3)
        with(binding) {
            setRecyclerView()
            movieSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let{ viewModel.setQuery(query) }
                    return true
                }
                override fun onQueryTextChange(query: String?): Boolean = false
            })
            viewModel.list.observe(this@Paging3Activity, {
                val data = it
                movieAdapter.submitData(lifecycle, it)
                movieFilter.setOnClickListener {
                    val filterData = data.filter { it.type == "movie" }
                    movieRecycler.scrollToPosition(0)
                    movieAdapter.submitData(lifecycle, filterData)
                }
                movieReset.setOnClickListener {
                    movieRecycler.scrollToPosition(0)
                    movieAdapter.submitData(lifecycle, data)
                }
            })
        }
    }

    private fun setRecyclerView() {
        binding.movieRecycler.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(this@Paging3Activity,3)
        }
    }
}