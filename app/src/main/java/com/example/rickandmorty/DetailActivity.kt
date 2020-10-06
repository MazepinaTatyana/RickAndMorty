package com.example.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.pojo.Result
import com.example.rickandmorty.view_model.ViewModelFactory
import com.example.rickandmorty.view_model.ViewModelRickAndMorty
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    lateinit var viewModel: ViewModelRickAndMorty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(application)
        )[ViewModelRickAndMorty::class.java]

        if (intent.hasExtra("characterId")) {
            val id = intent.getIntExtra("characterId", -1)
            if (id != null) {
               viewModel.getCharacterById(id).observe(this, object : Observer<Result> {
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
    
}