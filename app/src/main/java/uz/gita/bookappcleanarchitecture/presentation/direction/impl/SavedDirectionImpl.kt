package uz.gita.bookappcleanarchitecture.presentation.direction.impl

import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.navigation.AppNavigator
import uz.gita.bookappcleanarchitecture.presentation.direction.SavedDirection
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.main.MainScreenDirections
import javax.inject.Inject

class SavedDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) :SavedDirection{
    override suspend fun openDescriptionScreen(book: BookData) {
        appNavigator.navigateTo(MainScreenDirections.actionMainScreenToDescriptionScreen(book))
    }
    override suspend fun openReadScreen(book: BookData) {
        appNavigator.navigateTo(MainScreenDirections.actionMainScreenToReadScreen(book))
    }
}