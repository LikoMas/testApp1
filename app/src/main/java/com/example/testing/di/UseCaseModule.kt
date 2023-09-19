package com.example.testing.di

import com.example.testing.domain.repository.Repository
import com.example.testing.domain.useCase.AddUserUseCase
import com.example.testing.domain.useCase.CheckUserEmailUseCase
import com.example.testing.domain.useCase.CheckUserUseCase
import com.example.testing.domain.useCase.GetPostsListUseCase
import com.example.testing.domain.useCase.GetSinglePostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideAddUserUseCase(repository: Repository): AddUserUseCase{
        return AddUserUseCase(repository = repository)
    }

    @Provides
    fun provideCheckUserUseCase(repository: Repository): CheckUserUseCase{
        return CheckUserUseCase(repository = repository)
    }

    @Provides
    fun provideGetPostsListUseCase(repository: Repository): GetPostsListUseCase{
        return GetPostsListUseCase(repository = repository)
    }

    @Provides
    fun provideGetSinglePostUseCase(repository: Repository): GetSinglePostUseCase{
        return GetSinglePostUseCase(repository = repository)
    }

    @Provides
    fun provideCheckUserEmailUseCase(repository: Repository): CheckUserEmailUseCase{
        return CheckUserEmailUseCase(repository)
    }
}