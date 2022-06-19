package com.rosseti.tmgfoosball.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rosseti.domain.entity.ResultEntity
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.databinding.ItemResultBinding

class ResultViewAdapter(
    private val onItemClicked: (ResultEntity) -> Unit
) : PagingDataAdapter<ResultEntity, ResultViewAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemResultBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_result, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(scoreEntity: ResultEntity) {
            binding.apply {
                binding.item = scoreEntity
                scoreItem.setOnClickListener {
                    onItemClicked(scoreEntity)
                }
                executePendingBindings()
            }

        }
    }

    object COMPARATOR : DiffUtil.ItemCallback<ResultEntity>() {
        override fun areItemsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
            return oldItem == newItem
        }
    }

}