package com.example.rickandmorty

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.adapters.CharactersAdapter
import com.example.rickandmorty.pojo.Result
import com.example.rickandmorty.view_model.ViewModelFactory
import com.example.rickandmorty.view_model.ViewModelRickAndMorty
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: ViewModelRickAndMorty

    val adapter = CharactersAdapter()
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(application)
        )[ViewModelRickAndMorty::class.java]

        recyclerViewCharacters.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerViewCharacters.adapter = adapter
        progressBarLoading.visibility = View.VISIBLE

        viewModel.result.observe(this, object : Observer<List<Result>> {
            override fun onChanged(characters: List<Result>?) {
                if (characters != null) {
                    progressBarLoading.visibility = View.INVISIBLE
                    adapter.characters = characters
                }
            }

        })
        viewModel.errors.observe(this, object : Observer<Throwable> {
            override fun onChanged(t: Throwable?) {
                if (t != null) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    viewModel.clearErrors()
                }
            }

        })
        viewModel.loadData(page)

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
                if (page < viewModel.info.pages) {
                    viewModel.loadData(page++)
                }
            }

        }

    }

    override fun onDestroy() {
        viewModel.disposeDisposable()
        super.onDestroy()
    }
}
