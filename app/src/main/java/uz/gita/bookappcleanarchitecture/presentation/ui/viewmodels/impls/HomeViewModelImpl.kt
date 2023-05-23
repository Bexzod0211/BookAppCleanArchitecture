package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.HomeViewModel
import uz.gita.bookappcleanarchitecture.usecase.HomeUseCase
import uz.gita.bookappcleanarchitecture.usecase.impls.HomeUseCaseImpl
import uz.gita.bookappcleanarchitecture.utils.myLog
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val useCase:HomeUseCase
    ) : ViewModel(),HomeViewModel {
    override val booksLiveData: MutableLiveData<List<BookData>> = MutableLiveData()
    override val progressBarLiveData: MutableLiveData<Boolean> = MutableLiveData()
    override val toastLiveData:MutableLiveData<String> = MutableLiveData()
    override val openDescriptionScreenLiveData: MutableLiveData<BookData> = MutableLiveData()

    init {
        loadRecommendedBooks()
    }

    override fun loadRecommendedBooks() {
        progressBarLiveData.value = true
        useCase.getRecommendedBooks().onEach {
            it.onSuccess {list->
                booksLiveData.value = list
                progressBarLiveData.value = false
            }
            it.onFailure {e->
                toastLiveData.value = e.message
                myLog(e.message?:"")
                progressBarLiveData.value = false
            }
        }
            .launchIn(viewModelScope)
    }

    override fun itemClicked(book: BookData) {
        openDescriptionScreenLiveData.value = book
    }

}