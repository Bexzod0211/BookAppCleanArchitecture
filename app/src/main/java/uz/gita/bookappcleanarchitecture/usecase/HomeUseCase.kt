package uz.gita.bookappcleanarchitecture.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.bookappcleanarchitecture.data.model.BookData

interface HomeUseCase {
    fun getRecommendedBooks():Flow<Result<List<BookData>>>
    fun getBooksByQuery(query:String):Flow<Result<List<BookData>>>

}