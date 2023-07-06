package uz.gita.bookappcleanarchitecture.presentation.direction.impl

import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.navigation.AppNavigator
import uz.gita.bookappcleanarchitecture.presentation.direction.HomeDirection
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.home.HomeScreen
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.main.MainScreenDirections
import javax.inject.Inject

class HomeDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : HomeDirection{
    override suspend fun openMoreScreen(name: String) {
        appNavigator.navigateTo(MainScreenDirections.actionMainScreenToMoreScreen(name))
    }

    override suspend fun openDescriptionScreen(book: BookData) {
        appNavigator.navigateTo(MainScreenDirections.actionMainScreenToDescriptionScreen(book))
    }

    override suspend fun openReadScreen(book: BookData) {
       appNavigator.navigateTo(MainScreenDirections.actionMainScreenToReadScreen(book))
    }
}