package com.rosseti.tmgfoosball

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.tmgfoosball.databinding.ItemScoreBinding

class ScoreViewAdapter(
    private val onItemClicked: (ScoreEntity) -> Unit
) : PagingDataAdapter<ScoreEntity, ScoreViewAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemScoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_score, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("ObserveActions", "position $position")
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(val binding: ItemScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(scoreEntity: ScoreEntity) {
            binding.apply {
                Log.i("ObserveActions", "Entity $scoreEntity")
                binding.item = scoreEntity
                scoreItem.setOnClickListener {
                    onItemClicked(scoreEntity)
                }
                executePendingBindings()
            }

        }
    }

    object COMPARATOR : DiffUtil.ItemCallback<ScoreEntity>() {
        override fun areItemsTheSame(oldItem: ScoreEntity, newItem: ScoreEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ScoreEntity, newItem: ScoreEntity): Boolean {
            return oldItem == newItem
        }
    }

}