package uz.gita.bookappcleanarchitecture.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.data.model.CategoryData
import uz.gita.bookappcleanarchitecture.databinding.ItemExploreBinding
import javax.inject.Inject

class CategoryAdapter @Inject constructor() : ListAdapter<CategoryData, CategoryAdapter.Holder>(MyDiffUtil) {
    private lateinit var onItemClickListener:(BookData)->Unit

    object MyDiffUtil : DiffUtil.ItemCallback<CategoryData>() {

        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem.genre == newItem.genre
        }

        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem == newItem
        }

    }


    inner class Holder(private val binding: ItemExploreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            getItem(position).apply {
                binding.txtGenre.text = genre
                binding.apply {
                    val adapter = ExploreAdapter()
                    adapter.submitList(books)
                    recyclerBooks.adapter = adapter
                    recyclerBooks.layoutManager = LinearLayoutManager(binding.root.context,LinearLayoutManager.HORIZONTAL,false)
                    adapter.setOnItemClickListener {
                        onItemClickListener.invoke(it)
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    fun setOnItemClickListener(block:(BookData)->Unit){
        onItemClickListener = block
    }

}