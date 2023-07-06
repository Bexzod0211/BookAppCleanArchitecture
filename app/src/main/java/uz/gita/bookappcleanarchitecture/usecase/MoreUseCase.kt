package uz.gita.bookappcleanarchitecture.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookappcleanarchitecture.data.model.BookData

interface MoreUseCase {
    fun getRecommendedBooks():Flow<Result<List<BookData>>>
    fun getBooksByGenre(genre:String):Flow<Result<List<BookData>>>
    fun getBooksByQueryAndGenre(query:String,genre: String):Flow<Result<List<BookData>>>
}
