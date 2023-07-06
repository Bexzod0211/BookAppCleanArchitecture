package uz.gita.bookappcleanarchitecture.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.bookappcleanarchitecture.data.model.BookData

interface DescriptionUseCase {

    fun downLoadFile(context: Context, book: BookData): Flow<Result<Unit>>
    fun saveLastReadBook(book:BookData):Flow<Result<Unit>>
}