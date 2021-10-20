package id.co.abdialidrus.simplenote.presentation.note.list

sealed class NoteListEvent {

    object GetNotes: NoteListEvent()

    object OnRemoveHeadFromQueue: NoteListEvent()

}