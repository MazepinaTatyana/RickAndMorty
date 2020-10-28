package com.example.rickandmorty.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapters.CharactersAdapter
import com.example.rickandmorty.detailActivity.DetailActivity
import com.example.rickandmorty.pojo.Result
import kotlinx.android.synthetic.main.activity_main.*

//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

     private val mainViewModel by viewModels<MainViewModel> {
         MainViewModelFactory(
             MainRepository(application)
         )
     }

    val adapter = CharactersAdapter()
    var page = 1


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerViewCharacters.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerViewCharacters.adapter = adapter
        progressBarLoading.visibility = View.VISIBLE

        mainViewModel.result.observe(this, object : Observer<List<Result>> {
            override fun onChanged(characters: List<Result>?) {
                if (characters != null) {
                    progressBarLoading.visibility = View.INVISIBLE
                    adapter.characters = characters
                }
            }

        })
        mainViewModel.errors.observe(this, object : Observer<Throwable> {
            override fun onChanged(t: Throwable?) {
                if (t != null) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    mainViewModel.clearErrors()
                }
            }

        })
        mainViewModel.loadData(page)

        adapter.onClickCharacterListener = object : CharactersAdapter.OnClickCharacterListener {
            override fun onClickCharacter(position: Int) {
                val character = adapter.characters[position]
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("characterId", character.id)
                startActivity(intent)
            }
        }

        adapter.onReachEndListener = object : CharactersAdapter.OnReachEndListener {
            override fun onReachEnd() {
                if (page < mainViewModel.info.pages) {
                    mainViewModel.loadData(page++)
                }
            }

        }
    }
}
