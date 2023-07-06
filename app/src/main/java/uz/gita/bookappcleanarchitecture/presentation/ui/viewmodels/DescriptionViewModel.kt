package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.bookappcleanarchitecture.data.model.BookData

interface DescriptionViewModel {
    val messageLiveData:LiveData<String>
    val changeImgResLiveData:LiveData<Int>
    val enablingDownloadBtnLiveData:LiveData<Boolean>
    val openScreenReadLiveData:LiveData<BookData>
    val enablingReadBtnLiveData:LiveData<Boolean>

    fun btnDownLoadClicked(context: Context,book:BookData)
    fun btnReadClicked(book:BookData)
    fun btnBackClicked()

}