package com.zahand0.cowboys.presentation.ui.screen.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.ProductImagePreviewItemBinding

class ProductPreviewImageCollectionAdapter(private val onClick: (index: Int) -> Unit) :
    ListAdapter<String, ProductPreviewImageCollectionAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ProductImagePreviewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClick(position) }
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ProductImagePreviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            binding.imageProductPreview.load(imageUrl) {
                placeholder(R.drawable.product_image_item_placeholder)
                val corners =
                    binding.root.context.resources.getDimension(R.dimen.product_item_preview_image_item_corner_size)
                transformations(RoundedCornersTransformation(corners))
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }
}