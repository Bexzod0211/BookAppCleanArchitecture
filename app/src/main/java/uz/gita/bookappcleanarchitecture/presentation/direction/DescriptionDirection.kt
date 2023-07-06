package uz.gita.bookappcleanarchitecture.presentation.direction

import uz.gita.bookappcleanarchitecture.data.model.BookData

interface DescriptionDirection {
    suspend fun back()
    suspend fun openReadScreen(book:BookData)
}