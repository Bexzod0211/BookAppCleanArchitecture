package uz.gita.bookappcleanarchitecture.navigation

import kotlinx.coroutines.flow.Flow
import uz.gita.bookappcleanarchitecture.navigation.NavigationArg

interface NavigationHandler {
    val navBuffer:Flow<NavigationArg>
}