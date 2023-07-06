package uz.gita.bookappcleanarchitecture.presentation.direction

interface ExploreDirection {
    suspend fun openMoreScreen(genre:String)
}