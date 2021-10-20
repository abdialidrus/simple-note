package id.co.abdialidrus.simplenote.business.interactor.note

import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteDao
import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteEntity
import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteEntityMapper
import id.co.abdialidrus.simplenote.business.domain.util.DataState
import id.co.abdialidrus.simplenote.business.domain.util.MessageType
import id.co.abdialidrus.simplenote.business.domain.util.Response
import id.co.abdialidrus.simplenote.business.domain.util.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class CreateNote(
    private val noteDao: NoteDao,
    private val entityMapper: NoteEntityMapper
) {

    fun execute(
        title: String,
        body: String,
        categoryId: Int
    ): Flow<DataState<Response>> = flow {

        val noteCreatedAt = Calendar.getInstance().timeInMillis

        noteDao.insertNote(
            NoteEntity(
                title = title,
                body = body,
                categoryId = categoryId,
                createdAt = noteCreatedAt,
                updatedAt = noteCreatedAt
            )
        )

        emit(
            DataState.data<Response>(
                data = Response(
                    message = "Note successfully created",
                    uiComponentType = UIComponentType.Toast(),
                    messageType = MessageType.Success()
                ),
                response = null,
            )
        )

    }
}