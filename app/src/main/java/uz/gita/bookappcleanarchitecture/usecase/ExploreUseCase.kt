package uz.gita.bookappcleanarchitecture.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookappcleanarchitecture.data.model.CategoryData

interface ExploreUseCase {
   fun getAllBooks():Flow<Result<List<CategoryData>>>
}