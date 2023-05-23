package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.data.model.CategoryData
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.ExploreViewModel
import uz.gita.bookappcleanarchitecture.usecase.ExploreUseCase
import uz.gita.bookappcleanarchitecture.usecase.impls.ExploreUseCaseImpl
import uz.gita.bookappcleanarchitecture.utils.myLog
import javax.inject.Inject

@HiltViewModel
class ExploreViewModelImpl @Inject constructor(
    private val useCase: ExploreUseCase
) : ViewModel(), ExploreViewModel {
    override val allBooksLiveData: MutableLiveData<List<CategoryData>> = MutableLiveData()
    override val progressBarLiveData: MutableLiveData<Boolean> = MutableLiveData()
    override val messageLiveData: MutableLiveData<String> = MutableLiveData()
    override val openDescriptionScreenLiveData: MutableLiveData<BookData> = MutableLiveData()

    init {
        myLog("ViewModel init")
        loadAllBooks()
    }

    override fun loadAllBooks() {
        progressBarLiveData.value = true
        myLog("viewModelScope.launch")
        useCase.getAllBooks().onEach { result ->
            myLog("result")
            progressBarLiveData.value = false
            result.onSuccess {
                myLog("categorydata : $it")
                allBooksLiveData.value = it
            }
            result.onFailure {
                myLog(it.message?:"")
                messageLiveData.value = it.message
            }
        }.launchIn(viewModelScope)
    }

    override fun itemClicked(book: BookData) {
        openDescriptionScreenLiveData.value = book
    }

}