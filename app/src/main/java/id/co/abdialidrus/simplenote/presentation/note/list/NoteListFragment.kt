package id.co.abdialidrus.simplenote.presentation.note.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.abdialidrus.simplenote.R
import id.co.abdialidrus.simplenote.business.domain.model.Note
import id.co.abdialidrus.simplenote.business.domain.util.StateMessageCallback
import id.co.abdialidrus.simplenote.databinding.FragmentNoteListBinding
import id.co.abdialidrus.simplenote.presentation.note.BaseNoteFragment
import id.co.abdialidrus.simplenote.presentation.util.TopSpacingItemDecoration
import id.co.abdialidrus.simplenote.presentation.util.processQueue

class NoteListFragment : BaseNoteFragment(),
    NoteListAdapter.Interaction {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteListViewModel by viewModels()
    private var recyclerAdapter: NoteListAdapter? = null

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

        initRecyclerView()
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

            Log.d(TAG, "subscribeObservers: cached notes -> ${state.noteList}")

            if (state.noteList.isEmpty()) {

            } else {
                recyclerAdapter?.apply {
                    submitList(noteList = state.noteList)
                }
            }

        })
    }

    private fun initRecyclerView() {
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(this@NoteListFragment.context)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingDecorator) // does nothing if not applied already
            addItemDecoration(topSpacingDecorator)

            recyclerAdapter = NoteListAdapter(this@NoteListFragment)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
//                    Log.d(TAG, "onScrollStateChanged: exhausted? ${viewModel.state.value?.isQueryExhausted}")
//                    if (
//                        lastPosition == recyclerAdapter?.itemCount?.minus(1)
//                        && viewModel.state.value?.isLoading == false
//                        && viewModel.state.value?.isQueryExhausted == false
//                    ) {
//                        Log.d(TAG, "BlogFragment: attempting to load next page...")
//                        viewModel.onTriggerEvent(BlogEvents.NextPage)
//                    }
                }
            })
            adapter = recyclerAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Note) {

    }

}