package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.bookappcleanarchitecture.data.model.BookData

interface HomeViewModel {
    val booksLiveData:LiveData<List<BookData>>
    val progressBarLiveData:LiveData<Boolean>
    val toastLiveData:LiveData<String>
    val openDescriptionScreenLiveData:LiveData<BookData>

    fun loadRecommendedBooks()
    fun itemClicked(book:BookData)
}