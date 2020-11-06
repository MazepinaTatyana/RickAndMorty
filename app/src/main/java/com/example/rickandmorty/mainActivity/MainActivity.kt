package com.example.rickandmorty.mainActivity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapters.CharactersAdapter
import com.example.rickandmorty.api.State
import com.example.rickandmorty.detailActivity.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()


    var adapter = CharactersAdapter()
    val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewCharacters.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerViewCharacters.adapter = adapter



        if (isOnline(this)) {
            initAdapter()
            initState()
        }

        else {
            mainViewModel.result.observe(this@MainActivity, Observer {
                adapter.submitList(it)
                progressBarLoading.visibility = INVISIBLE
            })
        }

    }



    private fun initAdapter() {
        mainViewModel.retry()
        mainViewModel.charactersList.observe(this, Observer {
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

    fun isOnline(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}
