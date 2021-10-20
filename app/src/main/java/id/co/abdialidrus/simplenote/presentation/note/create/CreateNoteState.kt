package id.co.abdialidrus.simplenote.presentation.note.create

import id.co.abdialidrus.simplenote.business.domain.util.Queue
import id.co.abdialidrus.simplenote.business.domain.util.StateMessage

data class CreateNoteState (
    val isLoading: Boolean = false,
    val title: String = "",
    val body: String = "",
    val onCreateSuccess: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)