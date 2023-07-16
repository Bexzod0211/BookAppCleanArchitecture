package uz.gita.bookappcleanarchitecture.presentation.direction

import uz.gita.bookappcleanarchitecture.data.model.BookData

interface ExploreDirection {
    suspend fun openMoreScreen(genre:String)
    suspend fun openDescriptionScreen(book:BookData)
}