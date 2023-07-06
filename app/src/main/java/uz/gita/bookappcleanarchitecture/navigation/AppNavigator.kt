package uz.gita.bookappcleanarchitecture.navigation

interface AppNavigator {
    suspend fun navigateTo(screen: AppScreen)
    suspend fun back()
}