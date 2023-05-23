package uz.gita.bookappcleanarchitecture.usecase.impls

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.domain.repository.AppRepository
import uz.gita.bookappcleanarchitecture.domain.repository.AppRepositoryImpl
import uz.gita.bookappcleanarchitecture.usecase.DescriptionUseCase
import javax.inject.Inject

class DescriptionUseCaseImpl @Inject constructor(
    private val repository:AppRepository
) : DescriptionUseCase{

    override fun downLoadFile(context: Context, book: BookData): Flow<Result<Unit>>  = flow{
        emit(repository.downloadBook(context, book))
    }.
    flowOn(Dispatchers.IO)
        .catch {
            emit(Result.failure(it))
        }


}