package com.example.rickandmorty.detailActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rickandmorty.R
import com.example.rickandmorty.pojo.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        det_app_bar.setNavigationOnClickListener {
            finish()
        }

        if (intent.hasExtra("id")) {
            val id = intent.getIntExtra("id", -1)
            detailViewModel.getCharacterById(id).observe(this, object : Observer<Result> {
                @SuppressLint("RestrictedApi")
                override fun onChanged(character: Result?) {
                    if (character != null) {
                        getCharacter(character)
                    }
                }
            })
        }
    }

    fun getCharacter(character: Result) {
        textViewCreated.text = character.created
        textViewEpisode.text = character.episode.joinToString(";\n")
        textViewGender.text = character.gender
        textViewName.text = character.name
        textViewSpecies.text = character.species
        textViewStatus.text = character.status
        textViewType.text = character.type
        Glide.with(this@DetailActivity)
            .asBitmap()
            .load(character.image)
            .apply(RequestOptions.circleCropTransform())
            .into(imageViewCharacter)
    }

}