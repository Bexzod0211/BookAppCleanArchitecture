package uz.gita.bookappcleanarchitecture.usecase.impls

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.domain.repository.AppRepository
import uz.gita.bookappcleanarchitecture.domain.repository.AppRepositoryImpl
import uz.gita.bookappcleanarchitecture.usecase.HomeUseCase
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(private val repository: AppRepository) : HomeUseCase {

    override fun getRecommendedBooks(): Flow<Result<List<BookData>>> = flow {
        emit(repository.getAllRecommendedBooks())
    }
        .flowOn(Dispatchers.IO)
        .catch {
            emit(Result.failure(it))
        }

    override fun getBooksByQuery(query: String): Flow<Result<List<BookData>>> = flow{
        emit(repository.getRecommendedBooksByQuery(query))
    }


}