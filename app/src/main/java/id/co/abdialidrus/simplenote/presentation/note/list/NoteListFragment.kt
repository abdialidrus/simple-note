package id.co.abdialidrus.simplenote.presentation.note.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.co.abdialidrus.simplenote.R
import id.co.abdialidrus.simplenote.business.domain.util.StateMessageCallback
import id.co.abdialidrus.simplenote.databinding.FragmentNoteListBinding
import id.co.abdialidrus.simplenote.presentation.note.BaseNoteFragment
import id.co.abdialidrus.simplenote.presentation.util.processQueue

class NoteListFragment : BaseNoteFragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        viewModel.onTriggerEvent(NoteListEvent.GetNotes)

        binding.fabCreateNote.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_createNoteFragment)
        }
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner, { state ->

            uiCommunicationListener.displayProgressBar(state.isLoading)

            processQueue(
                context = context,
                queue = state.queue,
                stateMessageCallback = object : StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.onTriggerEvent(NoteListEvent.OnRemoveHeadFromQueue)
                    }
                })

            if (state.noteList.isEmpty()){
                Log.d(TAG, "subscribeObservers: cached notes -> ${state.noteList}")
            } else {
                //            recyclerAdapter?.apply {
//                submitList(blogList = state.blogList)
//            }
            }

        })
    }

}