package id.co.abdialidrus.simplenote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteDao
import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteEntityMapper
import id.co.abdialidrus.simplenote.business.interactor.note.CreateNote
import id.co.abdialidrus.simplenote.business.interactor.note.GetNotes

@Module
@InstallIn(ViewModelComponent::class)
object InteractorModule {

    @ViewModelScoped
    @Provides
    fun provideGetNotes(
        noteDao: NoteDao,
        entityMapper: NoteEntityMapper
    ): GetNotes {
        return GetNotes(
            noteDao,
            entityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideCreateNote(
        noteDao: NoteDao,
        entityMapper: NoteEntityMapper
    ): CreateNote {
        return CreateNote(
            noteDao,
            entityMapper
        )
    }

}