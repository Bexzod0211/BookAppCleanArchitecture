package uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.data.model.CategoryData

interface ExploreViewModel  {
    val allBooksLiveData:LiveData<List<CategoryData>>
    val progressBarLiveData:LiveData<Boolean>
    val messageLiveData:LiveData<String>
//    val openDescriptionScreenLiveData:LiveData<BookData>

    fun loadAllBooks()
    fun itemClicked(book:BookData)
    fun openMoreScreen(genre:String)
}