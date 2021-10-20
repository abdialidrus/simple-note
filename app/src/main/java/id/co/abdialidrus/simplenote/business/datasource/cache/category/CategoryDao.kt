package id.co.abdialidrus.simplenote.business.datasource.cache.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Query("""
        SELECT * FROM categories  
        ORDER BY id DESC
        """)
    suspend fun getAllCategories(): List<CategoryEntity>

}