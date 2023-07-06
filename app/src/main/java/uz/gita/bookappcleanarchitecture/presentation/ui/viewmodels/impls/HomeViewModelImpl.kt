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
import uz.gita.bookappcleanarchitecture.presentation.direction.HomeDirection
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.HomeViewModel
import uz.gita.bookappcleanarchitecture.usecase.HomeUseCase
import uz.gita.bookappcleanarchitecture.usecase.impls.HomeUseCaseImpl
import uz.gita.bookappcleanarchitecture.utils.myLog
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val useCase:HomeUseCase,
    private val direction:HomeDirection
    ) : ViewModel(),HomeViewModel {

    override val booksLiveData: MutableLiveData<List<BookData>> = MutableLiveData()
    override val progressBarLiveData: MutableLiveData<Boolean> = MutableLiveData()
    override val toastLiveData: MutableLiveData<String> = MutableLiveData()
    override val openDescriptionScreenLiveData: MutableLiveData<BookData> = MutableLiveData()
    override val placeHolderVisibilityLiveData: MutableLiveData<Int> = MutableLiveData()
    override val openShareMenuLiveData: MutableLiveData<Unit> = MutableLiveData()

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
        viewModelScope.launch {
            direction.openDescriptionScreen(book)
        }
    }

    override fun moreClickedBy(name: String) {
        viewModelScope.launch {
            direction.openMoreScreen(name)
        }
    }

    override fun onQueryChanged(query: String) {
        useCase.getBooksByQuery(query).onEach {result ->
            result.onSuccess {
                if (it.isEmpty()){
                    placeHolderVisibilityLiveData.value = View.VISIBLE
                    booksLiveData.value = emptyList()
                }else {
                    placeHolderVisibilityLiveData.value = View.GONE
                    booksLiveData.value = it
                }
            }
            result.onFailure {
                toastLiveData.value = it.message
                myLog(it.message?:"")
            }
        }.launchIn(viewModelScope)
    }

    override fun btnShareClicked() {
        openShareMenuLiveData.value = Unit
    }

}