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
import com.zahand0.cowboys.presentation.ui.util.toDateString

class OrdersAdapter(
    private val onMoreClick: (orderId: String) -> Unit,
    private val onClick: (productId: String) -> Unit
) : ListAdapter<OrderState, OrdersAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            binding = OrderItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onClick = { onClick(getItem(it).order.product.id) },
            onMoreClick = { onMoreClick(getItem(it).order.id) }
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

        fun bind(orderState: OrderState) {

            with(binding) {
                textCancelDate.text = root.context.resources.getString(
                    R.string.order_cancelled,
                    toDateString(orderState.order.etd, "dd.MM.YYYY"),
                    toDateString(orderState.order.etd, "hh:mm")
                )
                textDeliveryAddress.text = root.context.resources.getString(
                    R.string.order_delivery_address,
                    orderState.order.deliveryAddress
                )
                textDeliveryDate.text = root.context.resources.getString(
                    R.string.order_delivery_date,
                    toDateString(orderState.order.etd, "dd.MM.YYYY"),
                    toDateString(orderState.order.etd, "hh:mm")
                )
                textProductName.text = root.context.resources.getString(
                    R.string.order_name,
                    orderState.order.productQuantity.toString(),
                    orderState.order.productSize,
                    orderState.order.product.title
                )
                textProductStatus.setText(
                    when (orderState.order.status) {
                        "in_work" -> R.string.order_status_in_work
                        "cancelled" -> R.string.order_status_cancelled
                        "done" -> R.string.order_status_done
                        else -> R.string.empty
                    }
                )
                textProductNumberDate.text = root.context.resources.getString(
                    R.string.order_number_date,
                    orderState.order.number.toString(),
                    toDateString(orderState.order.createdAt, "dd.MM.YYYY"),
                    toDateString(orderState.order.createdAt, "hh:mm")
                )
                imageProduct.load(orderState.order.productPreview) {
                    val corners =
                        binding.root.context.resources.getDimension(R.dimen.product_item_image_corner_size)
                    transformations(RoundedCornersTransformation(corners))
                }
                buttonMore.isVisible = orderState.order.status == "in_work"
                buttonMore.isLoading = orderState.isLoading
                when (orderState.order.status) {
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

    private class DiffCallback : DiffUtil.ItemCallback<OrderState>() {
        override fun areItemsTheSame(
            oldItem: OrderState,
            newItem: OrderState
        ): Boolean {
            return oldItem.order.id == newItem.order.id
        }

        override fun areContentsTheSame(
            oldItem: OrderState,
            newItem: OrderState
        ): Boolean {
            return oldItem == newItem
        }
    }
}