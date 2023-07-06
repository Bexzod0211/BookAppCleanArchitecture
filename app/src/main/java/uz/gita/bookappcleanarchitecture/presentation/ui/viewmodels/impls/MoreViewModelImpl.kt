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
import uz.gita.bookappcleanarchitecture.presentation.direction.MoreDirection
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.MoreViewModel
import uz.gita.bookappcleanarchitecture.usecase.MoreUseCase
import uz.gita.bookappcleanarchitecture.utils.myLog
import javax.inject.Inject

@HiltViewModel
class MoreViewModelImpl @Inject constructor(
    private val useCase: MoreUseCase,
    private val direction: MoreDirection
) : MoreViewModel, ViewModel() {
    override val booksLiveData: MutableLiveData<List<BookData>> = MutableLiveData()
    override val progressBarLiveData: MutableLiveData<Boolean> = MutableLiveData()
    override val messageLiveData: MutableLiveData<String> = MutableLiveData()
    override val placeHolderVisibilityLiveData: MutableLiveData<Int> = MutableLiveData()


    override fun loadBooksBy(name: String) {
        progressBarLiveData.value = true
        var _name = name.lowercase()
        if (name == "recommendation") {
            useCase.getRecommendedBooks().onEach {
                it.onSuccess { list ->
                    booksLiveData.value = list
                    progressBarLiveData.value = false
                }
                it.onFailure { e ->
                    messageLiveData.value = e.message
                    myLog(e.message ?: "")
                    progressBarLiveData.value = false
                }
            }
                .launchIn(viewModelScope)
        } else {
            useCase.getBooksByGenre(_name).onEach { result ->
                result.onSuccess {
                    progressBarLiveData.value = false
                    booksLiveData.value = it

                }
                result.onFailure {
                    messageLiveData.value = it.message
                    progressBarLiveData.value = false
                    myLog(it.message ?: "")
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun itemClicked(book: BookData) {
        viewModelScope.launch {
            direction.openDescriptionScreen(book)
        }
    }

    override fun btnBackClicked() {
        viewModelScope.launch {
            direction.backToMainScreen()
        }
    }

    override fun onQueryChanged(query: String, genre: String) {
        useCase.getBooksByQueryAndGenre(query, genre)
            .onEach { result ->
                result.onSuccess {
                    if (it.isEmpty()) {
                        placeHolderVisibilityLiveData.value = View.VISIBLE
                        booksLiveData.value = emptyList()
                    }else {
                        placeHolderVisibilityLiveData.value = View.GONE
                        booksLiveData.value = it
                    }
                }
                result.onFailure {

                }
            }.launchIn(viewModelScope)
    }

}