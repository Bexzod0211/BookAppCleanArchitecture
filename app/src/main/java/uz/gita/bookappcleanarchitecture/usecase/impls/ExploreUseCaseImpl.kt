package uz.gita.bookappcleanarchitecture.usecase.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.bookappcleanarchitecture.data.model.CategoryData
import uz.gita.bookappcleanarchitecture.domain.repository.AppRepository
import uz.gita.bookappcleanarchitecture.domain.repository.AppRepositoryImpl
import uz.gita.bookappcleanarchitecture.usecase.ExploreUseCase
import javax.inject.Inject

class ExploreUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : ExploreUseCase {

    override fun getAllBooks(): Flow<Result<List<CategoryData>>> = flow {
        emit( repository.getAllBooks() )
    }
}