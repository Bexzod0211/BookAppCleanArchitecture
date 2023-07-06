package uz.gita.bookappcleanarchitecture.usecase.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.domain.repository.AppRepository
import uz.gita.bookappcleanarchitecture.usecase.MoreUseCase
import javax.inject.Inject

class MoreUseCaseImpl @Inject constructor(
    private val repository: AppRepository
):MoreUseCase{

    override fun getRecommendedBooks(): Flow<Result<List<BookData>>> = flow{
        emit(repository.getAllRecommendedBooks())
    }

    override fun getBooksByGenre(genre:String): Flow<Result<List<BookData>>> = flow{
        emit(repository.getBooksByGenre(genre))
    }

    override fun getBooksByQueryAndGenre(query: String, genre: String): Flow<Result<List<BookData>>> = flow{
        emit(repository.getBooksByQueryAndGenre(query, genre))
    }

}