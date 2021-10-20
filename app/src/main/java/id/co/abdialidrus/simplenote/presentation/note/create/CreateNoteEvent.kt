package id.co.abdialidrus.simplenote.presentation.note.create

import android.net.Uri
import id.co.abdialidrus.simplenote.business.domain.util.StateMessage

sealed class CreateNoteEvent {

    object CreateNote: CreateNoteEvent()

    data class OnUpdateTitle(
        val title: String,
    ): CreateNoteEvent()

    data class OnUpdateBody(
        val body: String,
    ): CreateNoteEvent()


    object OnCreateSuccess: CreateNoteEvent()

    data class Error(val stateMessage: StateMessage): CreateNoteEvent()

    object OnRemoveHeadFromQueue: CreateNoteEvent()

}