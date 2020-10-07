package com.example.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.pojo.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item.view.*

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    var characters: List<Result> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onClickCharacterListener: OnClickCharacterListener? = null
    var onReachEndListener: OnReachEndListener? = null

    interface OnClickCharacterListener {
        fun onClickCharacter(position: Int)
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClickCharacterListener?.let { it.onClickCharacter(adapterPosition) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharactersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        if (characters.size >= 20 && position == characters.size - 3 && onReachEndListener != null) {
            onReachEndListener?.onReachEnd()
        }
        val character = characters[position]
        holder.itemView.textViewRVChar.text = character.name
        Picasso.get().load(character.image).into(holder.itemView.imageViewRVChar)
    }
}