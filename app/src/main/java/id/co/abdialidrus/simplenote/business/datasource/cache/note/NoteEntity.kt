package id.co.abdialidrus.simplenote.business.datasource.cache.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import id.co.abdialidrus.simplenote.business.datasource.cache.category.CategoryEntity

@Entity(
    tableName = "notes"
//    ,
//    foreignKeys = [
//        ForeignKey(
//            entity = CategoryEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["category_id"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)
data class NoteEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "body")
    val body: String,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
)