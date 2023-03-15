package com.zahand0.bookstore.presentation.screen.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahand0.bookstore.data.Author
import com.zahand0.bookstore.data.Book
import com.zahand0.bookstore.data.BookAvailability
import com.zahand0.bookstore.data.MockRepository
import com.zahand0.bookstore.domain.model.BookListUiState
import com.zahand0.bookstore.domain.model.StoreAuthorBibliography
import com.zahand0.bookstore.domain.model.StoreBook
import com.zahand0.bookstore.presentation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LibraryViewModel : ViewModel() {

    private val repository = MockRepository()

    private val _bookList: MutableStateFlow<Resource<BookListUiState>> =
        MutableStateFlow(Resource.Loading())
    val bookList: StateFlow<Resource<BookListUiState>> = _bookList

    fun refreshBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            _bookList.value = Resource.Loading(_bookList.value.data)
            val authors = repository.getAuthors()
            val books = repository.getBooks()
            val availabilities = repository.getAvailability()

            val requestErrors = mutableListOf<String>()
            authors.onFailure {
                requestErrors.add(repository::getAuthors::name.get())
            }
            books.onFailure {
                requestErrors.add(repository::getBooks::name.get())
            }
            availabilities.onFailure {
                requestErrors.add(repository::getAvailability::name.get())
            }
            if (requestErrors.isNotEmpty()) {
                _bookList.value =
                    Resource.Error(
                        message = requestErrors.joinToString(separator = ", ") { "$it()" },
                        data = _bookList.value.data
                    )
                return@launch
            }
            _bookList.value = Resource.Success(
                createBookList(
                    authors.getOrThrow(),
                    books.getOrThrow(),
                    availabilities.getOrThrow()
                )
            )
        }
    }

    private fun createBookList(
        authors: List<Author>,
        books: List<Book>,
        availabilities: List<BookAvailability>
    ): BookListUiState {
        val storeBooks = books.mapNotNull { book ->
            availabilities.find { it.bookId == book.bookId }?.let { bookAvailability ->
                StoreBook(
                    bookId = book.bookId,
                    authorId = book.authorId,
                    name = book.title,
                    inStock = bookAvailability.inStock
                )
            }
        }
        val storeBookMap = storeBooks.groupBy { it.authorId }
        val bookList = authors.map { author ->
            StoreAuthorBibliography(
                authorId = author.authorId,
                authorName = author.name,
                books = storeBookMap.getOrElse(author.authorId) { listOf() }.sortedBy { it.name }
            )
        }.sortedBy { it.authorName }
        return BookListUiState(bookList)
    }

    companion object {
        private const val TAG = "LibraryViewModel"
    }
}