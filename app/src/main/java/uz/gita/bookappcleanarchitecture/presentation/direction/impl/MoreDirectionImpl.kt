package uz.gita.bookappcleanarchitecture.presentation.direction.impl

import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.navigation.AppNavigator
import uz.gita.bookappcleanarchitecture.presentation.direction.MoreDirection
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.more.MoreScreenDirections
import javax.inject.Inject

class MoreDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : MoreDirection{
    override suspend fun openDescriptionScreen(book: BookData) {
        appNavigator.navigateTo(MoreScreenDirections.actionMoreScreenToDescriptionScreen(book))
    }

    override suspend fun backToMainScreen() {
        appNavigator.back()
    }
}