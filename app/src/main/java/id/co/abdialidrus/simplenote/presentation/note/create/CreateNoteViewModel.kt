package id.co.abdialidrus.simplenote.presentation.note.create

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.abdialidrus.simplenote.business.domain.util.StateMessage
import id.co.abdialidrus.simplenote.business.domain.util.UIComponentType
import id.co.abdialidrus.simplenote.business.domain.util.doesMessageAlreadyExistInQueue
import id.co.abdialidrus.simplenote.business.interactor.note.CreateNote
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel
@Inject
constructor(
    private val createNote: CreateNote
) : ViewModel() {

    private val TAG: String = "AppDebug"

    val state: MutableLiveData<CreateNoteState> = MutableLiveData(CreateNoteState())

    fun onTriggerEvent(event: CreateNoteEvent) {
        when (event) {
            is CreateNoteEvent.OnUpdateTitle -> {
                onUpdateTitle(event.title)
            }
            is CreateNoteEvent.OnUpdateBody -> {
                onUpdateBody(event.body)
            }
            is CreateNoteEvent.CreateNote -> {
                createNote()
            }
            is CreateNoteEvent.OnCreateSuccess -> {
                onCreateSuccess()
            }
            is CreateNoteEvent.Error -> {
                appendToMessageQueue(event.stateMessage)
            }
            is CreateNoteEvent.OnRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }

    private fun onUpdateTitle(title: String) {
        state.value?.let { state ->
            this.state.value = state.copy(title = title)
        }
    }

    private fun onUpdateBody(body: String) {
        state.value?.let { state ->
            this.state.value = state.copy(body = body)
        }
    }

    private fun onCreateSuccess() {
        clearNewNoteFields()
        state.value?.let { state ->
            this.state.value = state.copy(onCreateSuccess = true)
        }
    }

    // call after successfully publishing
    private fun clearNewNoteFields() {
        onUpdateTitle("")
        onUpdateBody("")
    }

    private fun createNote() {
        state.value?.let { state ->
            val title = state.title
            val body = state.body

            createNote.execute(
                title = title,
                body = body,
                categoryId = 1
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.loading)

                dataState.data?.let { response ->
                    if (response.message == "Note successfully created") {
                        onTriggerEvent(CreateNoteEvent.OnCreateSuccess)
                    } else {
                        appendToMessageQueue(
                            stateMessage = StateMessage(response)
                        )
                    }
                }

                dataState.stateMessage?.let { stateMessage ->
                    appendToMessageQueue(stateMessage)
                }
            }.launchIn(viewModelScope)


        }
    }

    private fun appendToMessageQueue(stateMessage: StateMessage) {
        state.value?.let { state ->
            val queue = state.queue
            if (!stateMessage.doesMessageAlreadyExistInQueue(queue = queue)) {
                if (stateMessage.response.uiComponentType !is UIComponentType.None) {
                    queue.add(stateMessage)
                    this.state.value = state.copy(queue = queue)
                }
            }
        }
    }

    private fun removeHeadFromQueue() {
        state.value?.let { state ->
            try {
                val queue = state.queue
                queue.remove() // can throw exception if empty
                this.state.value = state.copy(queue = queue)
            } catch (e: Exception) {
                Log.d(TAG, "removeHeadFromQueue: Nothing to remove from DialogQueue")
            }
        }
    }
}