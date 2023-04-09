package com.zahand0.cowboys.presentation.ui.screen.catalog.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.ProductItemBinding
import com.zahand0.cowboys.domain.model.Product
import com.zahand0.cowboys.presentation.ui.util.CurrencyUtil

class ProductsAdapter(
    private val onBuyClick: (productId: String) -> Unit,
    private val onClick: (productId: String) -> Unit
) : ListAdapter<Product, ProductsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onClick(item.id) }
        holder.bind(getItem(position), onBuyClick = { onBuyClick(item.id) })
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, onBuyClick: () -> Unit) {

            with(binding) {
                textProductPrice.text = CurrencyUtil.currencyFormat.format(product.price)
                textProductTitle.text = product.title
                textProductCategory.text = product.category
                imageProduct.load(product.preview) {
                    val corners =
                        binding.root.context.resources.getDimension(R.dimen.product_item_image_corner_size)
                    transformations(RoundedCornersTransformation(corners))
                }
                textButtonBuy.setOnClickListener { onBuyClick() }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }
}