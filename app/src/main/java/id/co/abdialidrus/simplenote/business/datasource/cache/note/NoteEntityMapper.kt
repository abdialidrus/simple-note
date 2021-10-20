package id.co.abdialidrus.simplenote.business.datasource.cache.note

import id.co.abdialidrus.simplenote.business.domain.model.Note
import id.co.abdialidrus.simplenote.business.domain.util.DomainMapper

class NoteEntityMapper : DomainMapper<NoteEntity, Note> {
    override fun mapToDomainModel(model: NoteEntity): Note {
        return Note(
            id = model.id,
            title = model.title,
            body = model.body,
            categoryId = model.categoryId,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt
        )
    }

    override fun mapFromDomainModel(domainModel: Note): NoteEntity {
        return NoteEntity(
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body,
            categoryId = domainModel.categoryId,
            createdAt = domainModel.createdAt,
            updatedAt = domainModel.updatedAt
        )
    }

    fun fromEntityList(initial: List<NoteEntity>): List<Note> {
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Note>): List<NoteEntity> {
        return initial.map { mapFromDomainModel(it) }
    }
}