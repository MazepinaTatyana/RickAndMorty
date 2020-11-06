package com.example.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.api.State
import com.example.rickandmorty.pojo.Result
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.character_item.view.*
import javax.inject.Inject

class CharactersAdapter @Inject constructor(private val listener: OnClickCharacterListener) :
    PagedListAdapter<Result, RecyclerView.ViewHolder>(NewsDiffCallback) {

//    var characterPagedList = arrayListOf<Result>()
//    set(value) {
//        field = value
//        notifyDataSetChanged()
//    }

    private var state = State.LOADING

    interface OnClickCharacterListener {
        fun onClickCharacter(id: Int)
    }

    inner class CharactersViewHolder(itemView: View, val listener: OnClickCharacterListener) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private lateinit var character: Result
        fun bind(result: Result?) {
            if (result != null) {
                this.character = result
            }
            itemView.setOnClickListener(this)
            itemView.textViewRVChar.text = result?.name
            Picasso.get().load(result?.image).resize(300, 300).transform(CropCircleTransformation())
                .into(itemView.imageViewRVChar)
        }

        override fun onClick(v: View?) {
        listener.onClickCharacter(character.id)
        }
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.character_item, parent, false)
        return CharactersViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val characterId = getItem(position)?.id
        (holder as CharactersViewHolder).bind(getItem(position))
//        holder.itemView.setOnClickListener {
//            onClickCharacterListener?.let {
//                if (characterId != null) {
//                    it.onClickCharacter(
//                        holder.adapterPosition,
//                        characterId
//                    )
//                }
//
//            }
//        }
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}