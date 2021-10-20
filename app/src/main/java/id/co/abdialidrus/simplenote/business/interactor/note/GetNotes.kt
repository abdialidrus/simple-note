package id.co.abdialidrus.simplenote.business.interactor.note

import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteDao
import id.co.abdialidrus.simplenote.business.datasource.cache.note.NoteEntityMapper
import id.co.abdialidrus.simplenote.business.domain.model.Note
import id.co.abdialidrus.simplenote.business.domain.util.DataState
import id.co.abdialidrus.simplenote.business.domain.util.MessageType
import id.co.abdialidrus.simplenote.business.domain.util.Response
import id.co.abdialidrus.simplenote.business.domain.util.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNotes(
    private val noteDao: NoteDao,
    private val entityMapper: NoteEntityMapper
) {

    fun execute(): Flow<DataState<List<Note>>> = flow {
        emit(DataState.loading<List<Note>>())

        try {
            val cachedNotes = noteDao.getAllNotes()
            if (cachedNotes.isEmpty()) {
                emit(
                    DataState.error<List<Note>>(
                        response = Response(
                            message = "there are no saved notes",
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Error()
                        )
                    )
                )
            } else {
                emit(
                    DataState.data(
                        response = null,
                        data = entityMapper.fromEntityList(cachedNotes)
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                DataState.error<List<Note>>(
                    response = Response(
                        message = e.message,
                        uiComponentType = UIComponentType.None(),
                        messageType = MessageType.Error()
                    )
                )
            )
        }
    }

}