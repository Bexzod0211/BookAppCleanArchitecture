package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.bookappcleanarchitecture.data.model.BookData

interface MoreViewModel {
    val booksLiveData: LiveData<List<BookData>>
    val progressBarLiveData: LiveData<Boolean>
    val messageLiveData: LiveData<String>
    val placeHolderVisibilityLiveData:LiveData<Int>

    fun loadBooksBy(name:String)
    fun itemClicked(book: BookData)
    fun btnBackClicked()
    fun onQueryChanged(query:String,genre:String)
}