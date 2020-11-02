package com.example.rickandmorty.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapters.CharactersAdapter
import com.example.rickandmorty.api.State
import com.example.rickandmorty.detailActivity.DetailActivity
import com.example.rickandmorty.pojo.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    @Inject lateinit var mainViewModel: MainViewModel
    private val mainViewModel: MainViewModel by viewModels()
//            by viewModels<MainViewModel> {
//        MainViewModelFactory(
//            RickAndMortyRepository(ApiService.getService(), RickAndMortyDataBase.getInstance(this)),
//            RickAndMortyDataBase.getInstance(this)
//        )
//    }

    lateinit var adapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        initState()

        mainViewModel.characterPagedList.observe(
            this@MainActivity,
            object : Observer<PagedList<Result>> {
                override fun onChanged(t: PagedList<Result>?) {
                    adapter.submitList(t)
                    progressBarLoading.visibility = INVISIBLE
                }
            })

        mainViewModel.errors.observe(this, object : Observer<Throwable> {
            override fun onChanged(t: Throwable?) {
                Toast.makeText(this@MainActivity, t?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initAdapter() {
        adapter = CharactersAdapter{
            mainViewModel.retry()
        }
        recyclerViewCharacters.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerViewCharacters.adapter = adapter
        mainViewModel.characterPagedList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initState() {
        mainViewModel.getState().observe(this, object : Observer<State> {
            override fun onChanged(t: State?) {
                progressBarLoading.visibility =
                    if (mainViewModel.listIsEmpty() && t == State.LOADING) VISIBLE else GONE
                if (!mainViewModel.listIsEmpty()) {
                    adapter.setState(t ?: State.DONE)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        adapter.onClickCharacterListener = object : CharactersAdapter.OnClickCharacterListener {
            override fun onClickCharacter(position: Int, characterId: Int) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("id", characterId)
                startActivity(intent)
            }
        }
    }
}
