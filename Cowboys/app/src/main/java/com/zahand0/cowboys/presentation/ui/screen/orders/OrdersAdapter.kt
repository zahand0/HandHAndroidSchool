package com.zahand0.cowboys.presentation.ui.screen.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.OrderItemBinding
import com.zahand0.cowboys.domain.model.Order
import com.zahand0.cowboys.presentation.ui.util.toDateString

class OrdersAdapter(
    private val onMoreClick: (orderId: String) -> Unit,
    private val onClick: (productId: String) -> Unit
) : ListAdapter<Order, OrdersAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            binding = OrderItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onClick = { onClick(getItem(it).product.id) },
            onMoreClick = { onMoreClick(getItem(it).id) }
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onClick(item.id) }
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(
        private val binding: OrderItemBinding,
        onMoreClick: (adapterPosition: Int) -> Unit,
        onClick: (adapterPosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonMore.setOnClickListener {
                onMoreClick(adapterPosition)
            }
            binding.root.setOnClickListener {
                onClick(adapterPosition)
            }
        }

        fun bind(order: Order) {

            with(binding) {


                textCancelDate.text = root.context.resources.getString(
                    R.string.order_cancelled,
                    toDateString(order.etd, "dd.MM.YYYY"),
                    toDateString(order.etd, "hh:mm")
                )
                textDeliveryAddress.text = root.context.resources.getString(
                    R.string.order_delivery_address,
                    order.deliveryAddress
                )
                textDeliveryDate.text = root.context.resources.getString(
                    R.string.order_delivery_date,
                    toDateString(order.etd, "dd.MM.YYYY"),
                    toDateString(order.etd, "hh:mm")
                )
                textProductName.text = root.context.resources.getString(
                    R.string.order_name,
                    order.productQuantity.toString(),
                    order.productSize,
                    order.product.title
                )
                textProductStatus.setText(
                    when (order.status) {
                        "in_work" -> R.string.order_status_in_work
                        "cancelled" -> R.string.order_status_cancelled
                        "done" -> R.string.order_status_done
                        else -> R.string.empty
                    }
                )
                textProductNumberDate.text = root.context.resources.getString(
                    R.string.order_number_date,
                    order.number.toString(),
                    toDateString(order.createdAt, "dd.MM.YYYY"),
                    toDateString(order.createdAt, "hh:mm")
                )
                imageProduct.load(order.productPreview) {
                    val corners =
                        binding.root.context.resources.getDimension(R.dimen.product_item_image_corner_size)
                    transformations(RoundedCornersTransformation(corners))
                }

                when (order.status) {
                    "cancelled" -> {
                        imageProduct.imageAlpha = CANCELLED_ORDER_IMAGE_ALPHA
                        textProductStatus.setTextColor(
                            ResourcesCompat.getColor(
                                this.root.context.resources,
                                R.color.red_cancel,
                                null
                            )
                        )
                        textDeliveryDate.isVisible = false
                        textDeliveryAddress.isVisible = false
                        textCancelDate.isVisible = true
                    }

                    else -> {
                        imageProduct.imageAlpha = DEFAULT_ORDER_IMAGE_ALPHA
                        textProductStatus.setTextColor(
                            ResourcesCompat.getColor(
                                this.root.context.resources,
                                R.color.green_success,
                                null
                            )
                        )
                        textDeliveryDate.isVisible = true
                        textDeliveryAddress.isVisible = true
                        textCancelDate.isVisible = false
                    }
                }
            }
        }

        companion object {
            private const val CANCELLED_ORDER_IMAGE_ALPHA = 51
            private const val DEFAULT_ORDER_IMAGE_ALPHA = 255
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(
            oldItem: Order,
            newItem: Order
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Order,
            newItem: Order
        ): Boolean {
            return oldItem == newItem
        }
    }
}