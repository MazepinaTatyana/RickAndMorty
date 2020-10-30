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
import kotlinx.android.synthetic.main.character_item.view.*

class CharactersAdapter(function: () -> Unit) :
    PagedListAdapter<Result, RecyclerView.ViewHolder>(NewsDiffCallback) {

    private var state = State.LOADING
    var onClickCharacterListener: OnClickCharacterListener? = null

    interface OnClickCharacterListener {
        fun onClickCharacter(position: Int, v: View)
    }

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(result: Result?) {
            itemView.textViewRVChar.text = result?.name
            Picasso.get().load(result?.image).into(itemView.imageViewRVChar)
            itemView.textViewRVChar.textViewId = result?.id!!
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
        return CharactersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CharactersViewHolder).bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClickCharacterListener?.let {
                it.onClickCharacter(
                    holder.adapterPosition,
                    holder.itemView
                )
            }
        }
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}