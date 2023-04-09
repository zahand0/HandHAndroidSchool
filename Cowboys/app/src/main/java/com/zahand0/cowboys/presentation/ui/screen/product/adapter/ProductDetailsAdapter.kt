package com.zahand0.cowboys.presentation.ui.screen.product.adapter

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zahand0.cowboys.databinding.ProductDetailsItemBinding

class ProductDetailsAdapter() :
    ListAdapter<String, ProductDetailsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ProductDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))


    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ProductDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(detailsText: String) {
            val dotString = SpannableString(detailsText)
            dotString.setSpan(
                BulletSpan(16),
                0,
                detailsText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.root.text = dotString
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

