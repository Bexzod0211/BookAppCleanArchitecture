package uz.gita.bookappcleanarchitecture.presentation.direction.impl

import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.navigation.AppNavigator
import uz.gita.bookappcleanarchitecture.presentation.direction.DescriptionDirection
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.description.DescriptionScreenDirections
import javax.inject.Inject

class DescriptionDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : DescriptionDirection{
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun openReadScreen(book: BookData) {
        appNavigator.navigateTo(DescriptionScreenDirections.actionDescriptionScreenToReadScreen(book))
    }
}