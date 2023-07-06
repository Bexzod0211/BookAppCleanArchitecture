package uz.gita.bookappcleanarchitecture.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.bookappcleanarchitecture.usecase.DescriptionUseCase
import uz.gita.bookappcleanarchitecture.usecase.ExploreUseCase
import uz.gita.bookappcleanarchitecture.usecase.HomeUseCase
import uz.gita.bookappcleanarchitecture.usecase.MoreUseCase
import uz.gita.bookappcleanarchitecture.usecase.SavedUseCase
import uz.gita.bookappcleanarchitecture.usecase.impls.DescriptionUseCaseImpl
import uz.gita.bookappcleanarchitecture.usecase.impls.ExploreUseCaseImpl
import uz.gita.bookappcleanarchitecture.usecase.impls.HomeUseCaseImpl
import uz.gita.bookappcleanarchitecture.usecase.impls.MoreUseCaseImpl
import uz.gita.bookappcleanarchitecture.usecase.impls.SavedUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindDescriptionUseCase(impl:DescriptionUseCaseImpl):DescriptionUseCase

    @Binds
    fun bindExploreUseCase(impl:ExploreUseCaseImpl):ExploreUseCase

    @Binds
    fun bindHomeUseCase(impl:HomeUseCaseImpl):HomeUseCase

    @Binds
    fun bindSavedUseCase(impl:SavedUseCaseImpl):SavedUseCase

    @Binds
    fun bindMoreUseCase(impl:MoreUseCaseImpl):MoreUseCase
}