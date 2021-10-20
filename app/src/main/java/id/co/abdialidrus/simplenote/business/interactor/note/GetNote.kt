package id.co.abdialidrus.simplenote.business.interactor.note

import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteDao
import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteEntityMapper
import id.co.abdialidrus.simplenote.business.domain.model.Note
import id.co.abdialidrus.simplenote.business.domain.util.DataState
import id.co.abdialidrus.simplenote.business.domain.util.MessageType
import id.co.abdialidrus.simplenote.business.domain.util.Response
import id.co.abdialidrus.simplenote.business.domain.util.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetNote(
    private val noteDao: NoteDao,
    private val entityMapper: NoteEntityMapper
) {

    fun execute(
        id: Int
    ): Flow<DataState<Note>> = flow {
        emit(DataState.loading<Note>())

        val note = noteDao.getNoteById(id)

        if (note != null) {
            emit(DataState.data(response = null, data = entityMapper.mapToDomainModel(note)))
        } else {
            emit(DataState.error<Note>(
                response = Response(
                    message = "Unable to retrieve the blog post. Try reselecting it from the list",
                    uiComponentType = UIComponentType.Dialog(),
                    messageType = MessageType.Error()
                )
            ))
        }
    }.catch { e ->
        emit(DataState.error<Note>(
            response = Response(
                message = e.message,
                uiComponentType = UIComponentType.Dialog(),
                messageType = MessageType.Error()
            )
        ))
    }

}