package com.zahand0.bookstore.presentation.screen.library.book_list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zahand0.bookstore.R
import com.zahand0.bookstore.databinding.BookListItemBinding
import com.zahand0.bookstore.domain.model.StoreAuthorBibliography

class BookListAdapter :
    ListAdapter<StoreAuthorBibliography, BookListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = currentList.size

    class DiffCallback : DiffUtil.ItemCallback<StoreAuthorBibliography>() {
        override fun areItemsTheSame(
            oldItem: StoreAuthorBibliography,
            newItem: StoreAuthorBibliography
        ): Boolean {
            return oldItem.authorId == newItem.authorId
        }

        override fun areContentsTheSame(
            oldItem: StoreAuthorBibliography,
            newItem: StoreAuthorBibliography
        ): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: BookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: StoreAuthorBibliography) {
            with(binding) {
                setupUi(model)
            }
        }

        private fun BookListItemBinding.setupUi(model: StoreAuthorBibliography) {
            textAuthorName.text = model.authorName
            val books = model.books.foldRight("") { storeBook, books ->
                val availability = root.context.getString(
                    if (storeBook.inStock)
                        R.string.library_store_book_in_stock
                    else
                        R.string.library_store_book_not_in_stock
                )
                root.context.getString(
                    R.string.library_store_book_description,
                    storeBook.name,
                    availability
                ) + "\n" + books
            }
            textBooks.text = books
        }
    }
}