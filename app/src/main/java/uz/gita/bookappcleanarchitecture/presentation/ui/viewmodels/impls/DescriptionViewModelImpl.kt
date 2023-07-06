package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookappcleanarchitecture.R
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.presentation.direction.DescriptionDirection
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.DescriptionViewModel
import uz.gita.bookappcleanarchitecture.usecase.DescriptionUseCase
import uz.gita.bookappcleanarchitecture.usecase.impls.DescriptionUseCaseImpl
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModelImpl @Inject constructor(
    private val useCase: DescriptionUseCase,
    private val direction:DescriptionDirection
    ) : DescriptionViewModel, ViewModel(){
    override val messageLiveData:MutableLiveData<String> = MutableLiveData()
    override val changeImgResLiveData:MutableLiveData<Int> = MutableLiveData()
    override val enablingDownloadBtnLiveData:MutableLiveData<Boolean> = MutableLiveData()
    override val openScreenReadLiveData:MutableLiveData<BookData> = MutableLiveData()
    override val enablingReadBtnLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun btnDownLoadClicked(context: Context, book: BookData) {
        messageLiveData.value = "Start Downloading"
        enablingDownloadBtnLiveData.value = false
        useCase.downLoadFile(context, book).onEach {
            it.onSuccess {
                messageLiveData.value = "Book has been downloaded"
                changeImgResLiveData.value = R.drawable.ic_downloaded
                enablingDownloadBtnLiveData.value = false
                enablingReadBtnLiveData.value = true
            }
            it.onFailure { e->
                messageLiveData.value = e.message
                enablingDownloadBtnLiveData.value = true
            }
        }
            .launchIn(viewModelScope)
    }

    override fun btnReadClicked(book: BookData) {
        openScreenReadLiveData.value = book
    }

    override fun btnBackClicked() {
        viewModelScope.launch {
            direction.back()
        }
    }

}