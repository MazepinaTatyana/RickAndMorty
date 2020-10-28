package com.example.rickandmorty.detailActivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.rickandmorty.R
import com.example.rickandmorty.api.ApiFactory
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.pojo.Result
import com.example.rickandmorty.repository.RickAndMortyRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

//@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(
            RickAndMortyRepository(ApiFactory.apiService, RickAndMortyDataBase.getInstance(this))
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent.hasExtra("characterId")) {
            val id = intent.getIntExtra("characterId", -1)
            detailViewModel.getCharacterById(id).observe(this, object : Observer<Result> {
                override fun onChanged(characterInfo: Result?) {
                    if (characterInfo != null) {
                        textViewCreated.text = characterInfo.created
                        textViewEpisode.text = characterInfo.episode.joinToString(";\n")
                        textViewGender.text = characterInfo.gender
                        textViewName.text = characterInfo.name
                        textViewSpecies.text = characterInfo.species
                        textViewStatus.text = characterInfo.status
                        textViewType.text = characterInfo.type
                        Picasso.get().load(characterInfo.image).into(imageViewCharacter)
                    }
                }
            })
        }
    }
}