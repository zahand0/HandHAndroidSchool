package com.zahand0.cowboys.presentation.ui.screen.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.ProductSizesItemBinding
import com.zahand0.cowboys.domain.model.ProductSizeModel

class ProductSizesAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<ProductSizeModel, ProductSizesAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ProductSizesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            if (item.isAvailable) {
                onItemClick(position)
            }
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ProductSizesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(size: ProductSizeModel) {
            with(binding.root) {
                text = size.value
                if (size.isAvailable) {
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                } else {
                    setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
                }
            }

        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ProductSizeModel>() {
        override fun areItemsTheSame(
            oldItem: ProductSizeModel,
            newItem: ProductSizeModel
        ): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(
            oldItem: ProductSizeModel,
            newItem: ProductSizeModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}

