package id.co.abdialidrus.simplenote.presentation.note.create

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.co.abdialidrus.simplenote.R
import id.co.abdialidrus.simplenote.business.domain.util.StateMessageCallback
import id.co.abdialidrus.simplenote.databinding.FragmentNoteCreateBinding
import id.co.abdialidrus.simplenote.presentation.note.BaseNoteFragment
import id.co.abdialidrus.simplenote.presentation.util.processQueue


class CreateNoteFragment: BaseNoteFragment() {

    private var _binding: FragmentNoteCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel : CreateNoteViewModel by viewModels()
//    private val fabTimer = object: CountDownTimer(2000, 1000) {
//        override fun onTick(millisUntilFinished: Long) {
//            Log.d(TAG, "onTick: show fab in ${millisUntilFinished / 1000}")
//        }
//
//        override fun onFinish() {
//            val title = binding.etTitle.text.toString()
//            val body = binding.etBody.text.toString()
//
//            if (title != "" || body != ""){
//                binding.fabSave.show()
//                Log.d(TAG, "onTick: fab visible now")
//            } else {
//                binding.fabSave.hide()
//                Log.d(TAG, "onTick: fab invisible because title and body are empty")
//            }
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteCreateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        binding.etTitle.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                hideFab()
            }

            override fun afterTextChanged(p0: Editable?) {
                showFab()
            }

        })
        binding.etBody.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                hideFab()
            }

            override fun afterTextChanged(p0: Editable?) {
                showFab()
            }

        })
        binding.fabSave.setOnClickListener {
            cacheState()
            viewModel.onTriggerEvent(CreateNoteEvent.CreateNote)
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
                        viewModel.onTriggerEvent(CreateNoteEvent.OnRemoveHeadFromQueue)
                    }
                })
            setNoteProperties(
                title = state.title,
                body = state.body
            )
            if (state.onCreateSuccess) {
                findNavController().popBackStack()
                //findNavController().popBackStack(R.id.noteListFragment, false)
            }
        })
    }

    private fun setNoteProperties(title: String, body: String) {
        binding.etTitle.setText(title)
        binding.etBody.setText(body)

        if (title == "" && body == "") {
            binding.fabSave.hide()
        } else {
            binding.fabSave.show()
        }
    }

    private fun hideFab(){
        binding.fabSave.hide()

//        fabTimer.cancel()
//        binding.fabSave.hide()
//        Log.d(TAG, "onTick: fab hidden now")
    }

    private fun showFab(){
        binding.fabSave.show()
        //fabTimer.start()
    }

    private fun cacheState() {
        val title = binding.etTitle.text.toString()
        val body = binding.etBody.text.toString()
        viewModel.onTriggerEvent(CreateNoteEvent.OnUpdateTitle(title))
        viewModel.onTriggerEvent(CreateNoteEvent.OnUpdateBody(body))
    }

    override fun onPause() {
        super.onPause()
        cacheState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onTriggerEvent(CreateNoteEvent.ClearNoteAttributes)
        _binding = null
    }

}