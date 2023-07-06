package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.bookappcleanarchitecture.data.model.BookData

interface HomeViewModel {
    val booksLiveData:LiveData<List<BookData>>
    val progressBarLiveData:LiveData<Boolean>
    val toastLiveData:LiveData<String>
    val openDescriptionScreenLiveData:LiveData<BookData>
    val placeHolderVisibilityLiveData:LiveData<Int>
    val openShareMenuLiveData:LiveData<Unit>

    fun loadRecommendedBooks()
    fun itemClicked(book:BookData)
    fun moreClickedBy(name:String)
    fun onQueryChanged(query:String)
    fun btnShareClicked()
}