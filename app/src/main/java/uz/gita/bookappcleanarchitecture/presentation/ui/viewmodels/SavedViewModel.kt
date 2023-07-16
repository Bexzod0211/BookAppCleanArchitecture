package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.bookappcleanarchitecture.data.model.BookData

interface SavedViewModel {
    val savedBooksLiveData:LiveData<List<BookData>>
    val messageLiveData:LiveData<String>
//    val openDescriptionScreenLiveData:LiveData<BookData>
    val placeHolderLiveData:LiveData<Int>
    val lastReadBookLiveData:LiveData<BookData>
    val recentViewVisibilityLiveData:LiveData<Int>

    fun loadSavedBooks()
    fun itemClicked(book:BookData)
    fun openReadScreen()
}