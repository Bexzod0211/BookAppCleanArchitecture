package uz.gita.bookappcleanarchitecture.domain.repository

import android.content.Context
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.data.model.CategoryData

interface AppRepository {
    suspend fun getAllRecommendedBooks():Result<List<BookData>>
    suspend fun downloadBook(context:Context,book:BookData):Result<Unit>
    suspend fun getAllSavedBooks():Result<List<BookData>>
    suspend fun getAllBooks(): Result<List<CategoryData>>
    suspend fun getBooksByGenre(genre:String):Result<List<BookData>>
    suspend fun getRecommendedBooksByQuery(query:String):Result<List<BookData>>
    suspend fun getBooksByQueryAndGenre(query: String,genre: String):Result<List<BookData>>
}