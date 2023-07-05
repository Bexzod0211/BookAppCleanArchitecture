package uz.gita.bookappcleanarchitecture.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.bookappcleanarchitecture.R
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.databinding.ItemBookRecommendBinding
import javax.inject.Inject

class BookAdapter @Inject constructor() : ListAdapter<BookData, BookAdapter.Holder>(MyDiffUtil) {
    private lateinit var onItemClickListener:(BookData)->Unit
    object MyDiffUtil : DiffUtil.ItemCallback<BookData>() {
        override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem == newItem
        }

    }

    inner class Holder(private val binding: ItemBookRecommendBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener.invoke(getItem(adapterPosition))
            }

        }

        fun bind(position: Int){
            getItem(position).apply {
                Glide.with(binding.root.context)
                    .load(coverUrl)
                    .placeholder(R.drawable.ic_image)
                    .into(binding.imgCover)
                binding.txtTitle.text = title
                binding.txtAuthor.text ="by $author"
                binding.txtStar.text = "$rate"

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemBookRecommendBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    fun setOnItemClickListener(block:(BookData)->Unit){
        onItemClickListener = block
    }
}