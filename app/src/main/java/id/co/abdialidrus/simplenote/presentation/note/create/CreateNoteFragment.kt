package id.co.abdialidrus.simplenote.presentation.note.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.co.abdialidrus.simplenote.databinding.FragmentNoteCreateBinding
import id.co.abdialidrus.simplenote.databinding.FragmentNoteListBinding
import id.co.abdialidrus.simplenote.presentation.note.BaseNoteFragment

class CreateNoteFragment: BaseNoteFragment() {

    private var _binding: FragmentNoteCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteCreateBinding.inflate(layoutInflater)
        return binding.root
    }

}