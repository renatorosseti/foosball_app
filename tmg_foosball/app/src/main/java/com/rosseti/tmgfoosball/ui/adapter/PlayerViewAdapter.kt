package com.rosseti.tmgfoosball.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.databinding.ItemGamerBinding

class PlayerViewAdapter(
    private val onItemClicked: (PlayerEntity) -> Unit
) : PagingDataAdapter<PlayerEntity, PlayerViewAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemGamerBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_gamer, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(val binding: ItemGamerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playerEntity: PlayerEntity) {
            binding.apply {
                binding.item = playerEntity
                scoreItem.setOnClickListener {
                    onItemClicked(playerEntity)
                }
                executePendingBindings()
            }

        }
    }

    object COMPARATOR : DiffUtil.ItemCallback<PlayerEntity>() {
        override fun areItemsTheSame(oldItem: PlayerEntity, newItem: PlayerEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlayerEntity, newItem: PlayerEntity): Boolean {
            return oldItem == newItem
        }
    }

}