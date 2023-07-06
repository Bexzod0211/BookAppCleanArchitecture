package uz.gita.bookappcleanarchitecture.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bookappcleanarchitecture.presentation.direction.DescriptionDirection
import uz.gita.bookappcleanarchitecture.presentation.direction.ExploreDirection
import uz.gita.bookappcleanarchitecture.presentation.direction.HomeDirection
import uz.gita.bookappcleanarchitecture.presentation.direction.MoreDirection
import uz.gita.bookappcleanarchitecture.presentation.direction.impl.DescriptionDirectionImpl
import uz.gita.bookappcleanarchitecture.presentation.direction.impl.ExploreDirectionImpl
import uz.gita.bookappcleanarchitecture.presentation.direction.impl.HomeDirectionImpl
import uz.gita.bookappcleanarchitecture.presentation.direction.impl.MoreDirectionImpl

@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {

    @Binds
    fun bindHomeDirection(impl:HomeDirectionImpl):HomeDirection

    @Binds
    fun bindMoreDirection(impl:MoreDirectionImpl):MoreDirection

    @Binds
    fun bindExploreDirection(impl:ExploreDirectionImpl):ExploreDirection

    @Binds
    fun bindDescriptionDirection(impl:DescriptionDirectionImpl):DescriptionDirection
}