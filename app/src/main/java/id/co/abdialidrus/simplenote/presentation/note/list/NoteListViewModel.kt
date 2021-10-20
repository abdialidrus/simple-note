package id.co.abdialidrus.simplenote.presentation.note.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.abdialidrus.simplenote.business.domain.util.StateMessage
import id.co.abdialidrus.simplenote.business.domain.util.UIComponentType
import id.co.abdialidrus.simplenote.business.domain.util.doesMessageAlreadyExistInQueue
import id.co.abdialidrus.simplenote.business.interactor.note.GetNotes
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel
@Inject
constructor(
    private val getNotes: GetNotes
): ViewModel() {

    private val TAG: String = "AppDebug"

    val state: MutableLiveData<NoteListState> = MutableLiveData(NoteListState())

    fun onTriggerEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.GetNotes -> {
                getAllNotes()
            }
        }
    }

    private fun getAllNotes(){
        state.value?.let { state ->
            getNotes.execute().onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.loading)

                dataState.data?.let { list ->
                    this.state.value = state.copy(noteList = list)
                }

                dataState.stateMessage?.let { stateMessage ->
                    appendToMessageQueue(stateMessage)
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun clearList(){
        state.value?.let { state ->
            this.state.value = state.copy(noteList = listOf())
        }
    }

    private fun appendToMessageQueue(stateMessage: StateMessage){
        state.value?.let { state ->
            val queue = state.queue
            if(!stateMessage.doesMessageAlreadyExistInQueue(queue = queue)){
                if(stateMessage.response.uiComponentType !is UIComponentType.None){
                    queue.add(stateMessage)
                    this.state.value = state.copy(queue = queue)
                }
            }
        }
    }

}