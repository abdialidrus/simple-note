package id.co.abdialidrus.simplenote.presentation.note.list

import id.co.abdialidrus.simplenote.business.domain.model.Note
import id.co.abdialidrus.simplenote.business.domain.util.Queue
import id.co.abdialidrus.simplenote.business.domain.util.StateMessage

data class NoteListState(
    val isLoading: Boolean = false,
    val noteList: List<Note> = listOf(),
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)