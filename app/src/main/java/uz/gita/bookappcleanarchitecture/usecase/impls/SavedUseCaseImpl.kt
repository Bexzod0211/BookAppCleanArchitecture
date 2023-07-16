package uz.gita.bookappcleanarchitecture.usecase.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.domain.repository.AppRepository
import uz.gita.bookappcleanarchitecture.domain.repository.AppRepositoryImpl
import uz.gita.bookappcleanarchitecture.usecase.SavedUseCase
import javax.inject.Inject

class SavedUseCaseImpl @Inject constructor(
    private val repository:AppRepository
    ) : SavedUseCase{
    override fun loadSavedBooks(): Flow<Result<List<BookData>>>  = flow{
        emit(repository.getAllSavedBooks())
    }
    override fun getLastReadBook(): Flow<Result<BookData?>> = flow{
        emit(repository.getLastReadBook())
    }
}