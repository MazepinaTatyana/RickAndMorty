package com.example.rickandmorty.detailActivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.rickandmorty.R
import com.example.rickandmorty.pojo.Result
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
//    @Inject lateinit var detailViewModel: DetailViewModel
//            by viewModels<DetailViewModel> {
//        DetailViewModelFactory(
//            RickAndMortyRepository(ApiService.getService(), RickAndMortyDataBase.getInstance(this))
//        )
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent.hasExtra("id")) {
            val id = intent.getIntExtra("id", -1)
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