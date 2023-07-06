package uz.gita.bookappcleanarchitecture.presentation.direction

import uz.gita.bookappcleanarchitecture.data.model.BookData

interface HomeDirection {
    suspend fun openMoreScreen(name:String)
    suspend fun openDescriptionScreen(book:BookData)
}