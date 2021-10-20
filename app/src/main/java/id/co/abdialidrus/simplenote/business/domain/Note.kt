package id.co.abdialidrus.simplenote.business.domain

data class Note (
    val id: Int,
    val title: String,
    val body: String,
    val categoryId: Int,
    val createdAt: Long,
    val updatedAt: Long
)