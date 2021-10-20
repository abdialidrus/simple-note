package id.co.abdialidrus.simplenote.di

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.co.abdialidrus.simplenote.business.datasource.cache.AppDatabase
import id.co.abdialidrus.simplenote.business.datasource.cache.category.CategoryDao
import id.co.abdialidrus.simplenote.business.datasource.cache.category.CategoryEntityMapper
import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteDao
import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteEntityMapper
import id.co.abdialidrus.simplenote.presentation.BaseApplication
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(db: AppDatabase): NoteDao {
        return db.noteDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao {
        return db.categoryDao()
    }

    @Singleton
    @Provides
    fun provideCacheNoteMapper(): NoteEntityMapper {
        return NoteEntityMapper()
    }

    @Singleton
    @Provides
    fun provideCacheCategoryMapper(): CategoryEntityMapper {
        return CategoryEntityMapper()
    }

}