package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.SavedViewModel
import uz.gita.bookappcleanarchitecture.usecase.SavedUseCase
import uz.gita.bookappcleanarchitecture.usecase.impls.SavedUseCaseImpl
import javax.inject.Inject

@HiltViewModel
class SavedViewModelImpl @Inject constructor(
    private val useCase:SavedUseCase
    ) : ViewModel(), SavedViewModel {
    override val savedBooksLiveData: MutableLiveData<List<BookData>> = MutableLiveData()
    override val messageLiveData: MutableLiveData<String> = MutableLiveData()
    override val openDescriptionScreenLiveData: MutableLiveData<BookData> = MutableLiveData()

    init {
        loadSavedBooks()
    }

    override fun loadSavedBooks() {
        useCase.loadSavedBooks().onEach {result->
            result.onSuccess {
                savedBooksLiveData.value = it
            }
            result.onFailure {
                messageLiveData.value = it.message
            }
        }.launchIn(viewModelScope)
    }

    override fun itemClicked(book: BookData) {
        openDescriptionScreenLiveData.value = book
    }
}