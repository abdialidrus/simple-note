package id.co.abdialidrus.simplenote.business.datasource.cache.note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Query("""
        SELECT * FROM notes  
        ORDER BY id DESC
        """)
    suspend fun getAllNotes(): List<NoteEntity>

}