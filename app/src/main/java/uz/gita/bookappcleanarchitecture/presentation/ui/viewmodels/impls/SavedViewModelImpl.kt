package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.presentation.direction.SavedDirection
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.SavedViewModel
import uz.gita.bookappcleanarchitecture.usecase.SavedUseCase
import uz.gita.bookappcleanarchitecture.usecase.impls.SavedUseCaseImpl
import javax.inject.Inject

@HiltViewModel
class SavedViewModelImpl @Inject constructor(
    private val useCase:SavedUseCase,
    private val direction:SavedDirection
    ) : ViewModel(), SavedViewModel {
    override val savedBooksLiveData: MutableLiveData<List<BookData>> = MutableLiveData()
    override val messageLiveData: MutableLiveData<String> = MutableLiveData()
//    override val openDescriptionScreenLiveData: MutableLiveData<BookData> = MutableLiveData()
    override val placeHolderLiveData: MutableLiveData<Int> = MutableLiveData()
    override val lastReadBookLiveData: MutableLiveData<BookData> = MutableLiveData()
    override val recentViewVisibilityLiveData: MutableLiveData<Int> = MutableLiveData()
    init {
        loadSavedBooks()
        useCase.getLastReadBook().onEach {result ->
            result.onSuccess {lrBook->
                if (lrBook == null){
                    recentViewVisibilityLiveData.value =View.GONE
                }
                else {
                    recentViewVisibilityLiveData.value = View.VISIBLE
                    lastReadBookLiveData.value = lrBook
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun loadSavedBooks() {
        useCase.loadSavedBooks().onEach {result->
            result.onSuccess {
                if (it.isEmpty())
                    placeHolderLiveData.value = View.VISIBLE
                else {
                    placeHolderLiveData.value = View.GONE
                    savedBooksLiveData.value = it
                }
            }
            result.onFailure {
                messageLiveData.value = it.message
            }
        }.launchIn(viewModelScope)
    }

    override fun itemClicked(book: BookData) {
//        openDescriptionScreenLiveData.value = book
        viewModelScope.launch {
            direction.openDescriptionScreen(book)
        }
    }

    override fun openReadScreen() {
        useCase.getLastReadBook().onEach {result ->
            result.onSuccess {lrBook->
                lrBook?.let {
                    direction.openReadScreen(it)
                }
            }
        }.launchIn(viewModelScope)
    }
}