package uz.gita.bookappcleanarchitecture.presentation.direction.impl

import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.navigation.AppNavigator
import uz.gita.bookappcleanarchitecture.presentation.direction.ExploreDirection
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.main.MainScreenDirections
import javax.inject.Inject

class ExploreDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : ExploreDirection{
    override suspend fun openMoreScreen(genre: String) {
        appNavigator.navigateTo(MainScreenDirections.actionMainScreenToMoreScreen(genre))
    }

    override suspend fun openDescriptionScreen(book: BookData) {
        appNavigator.navigateTo(MainScreenDirections.actionMainScreenToDescriptionScreen(book))
    }
}