package uz.gita.bookappcleanarchitecture.presentation.direction

import uz.gita.bookappcleanarchitecture.data.model.BookData

interface SavedDirection {
    suspend fun openDescriptionScreen(book:BookData)
    suspend fun openReadScreen(book: BookData)
}