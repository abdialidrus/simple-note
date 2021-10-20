package id.co.abdialidrus.simplenote.business.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import id.co.abdialidrus.simplenote.business.datasource.cache.category.CategoryDao
import id.co.abdialidrus.simplenote.business.datasource.cache.category.CategoryEntity
import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteDao
import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteEntity

@Database(
    entities = [
        NoteEntity::class,
        CategoryEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        val DATABASE_NAME: String = "simple_note_db"
    }


}