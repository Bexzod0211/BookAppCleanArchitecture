package uz.gita.bookappcleanarchitecture.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bookappcleanarchitecture.navigation.AppNavigator
import uz.gita.bookappcleanarchitecture.navigation.NavigationDispatcher
import uz.gita.bookappcleanarchitecture.navigation.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindAppNavigator(impl: NavigationDispatcher): AppNavigator

    @Binds
    fun bindNavigationHandler(impl:NavigationDispatcher): NavigationHandler
}