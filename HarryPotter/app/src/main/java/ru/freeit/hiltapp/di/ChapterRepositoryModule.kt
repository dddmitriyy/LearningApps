package ru.freeit.hiltapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import ru.freeit.hiltapp.domain.repository.ChapterRepository
import ru.freeit.hiltapp.domain.repository.ChapterRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class ChapterRepositoryModule {

    @Binds
    abstract fun bindRepository(impl: ChapterRepositoryImpl) : ChapterRepository
}