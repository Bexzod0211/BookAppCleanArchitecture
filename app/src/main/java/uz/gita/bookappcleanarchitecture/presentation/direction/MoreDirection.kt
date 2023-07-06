package uz.gita.bookappcleanarchitecture.presentation.direction

import uz.gita.bookappcleanarchitecture.data.model.BookData

interface MoreDirection {
    suspend fun openDescriptionScreen(book:BookData)
    suspend fun backToMainScreen()
}