package uz.gita.bookappcleanarchitecture.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookappcleanarchitecture.data.model.BookData

interface SavedUseCase {
    fun loadSavedBooks():Flow<Result<List<BookData>>>
    fun getLastReadBook():Flow<Result<BookData?>>
}