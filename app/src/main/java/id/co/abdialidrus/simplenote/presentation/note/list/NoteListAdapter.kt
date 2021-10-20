package id.co.abdialidrus.simplenote.presentation.note.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import id.co.abdialidrus.simplenote.business.domain.model.Note
import id.co.abdialidrus.simplenote.databinding.ItemListNoteBinding

class NoteListAdapter(
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG: String = "AppDebug"

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
    private val differ =
        AsyncListDiffer(
            NoteRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(
            ItemListNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction = interaction,
        )
    }

    internal inner class NoteRecyclerChangeCallback(
        private val adapter: NoteListAdapter
    ) : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoteViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(noteList: List<Note>?, ){
        val newList = noteList?.toMutableList()
        differ.submitList(newList)
    }

    class NoteViewHolder
    constructor(
        private val binding: ItemListNoteBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) {
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            binding.tvNoteTitle.text = item.title
            binding.tvNoteBody.text = item.body
            //binding.blogUpdateDate.text = DateUtils.convertLongToStringDate(item.dateUpdated)
        }
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: Note)

    }

}