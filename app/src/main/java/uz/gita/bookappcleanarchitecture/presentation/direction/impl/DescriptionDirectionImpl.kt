package uz.gita.bookappcleanarchitecture.presentation.direction.impl

import uz.gita.bookappcleanarchitecture.navigation.AppNavigator
import uz.gita.bookappcleanarchitecture.presentation.direction.DescriptionDirection
import javax.inject.Inject

class DescriptionDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : DescriptionDirection{
    override suspend fun back() {
        appNavigator.back()
    }
}