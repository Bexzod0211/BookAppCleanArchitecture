package uz.gita.bookappcleanarchitecture.usecase.impls

import uz.gita.bookappcleanarchitecture.domain.repository.AppRepository
import javax.inject.Inject

class ReadUseCaseImpl @Inject constructor(
    private val repository:AppRepository
) {

}