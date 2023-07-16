package uz.gita.bookappcleanarchitecture.presentation.ui.screens.saved

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookappcleanarchitecture.R
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.databinding.ScreenSavedBinding
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.description.DescriptionScreen
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.SavedViewModel
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls.SavedViewModelImpl
import uz.gita.bookappcleanarchitecture.utils.replaceScreenAddToStack
import uz.gita.bookappcleanarchitecture.presentation.ui.adapter.SavedAdapter
import javax.inject.Inject

@AndroidEntryPoint
class SavedScreen : Fragment(R.layout.screen_saved) {
    private val binding:ScreenSavedBinding by viewBinding()
    private val viewModel:SavedViewModel by viewModels<SavedViewModelImpl>()
    @Inject
    lateinit var adapter:SavedAdapter

//    private val adapter by lazy { SavedAdapter(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.openDescriptionScreenLiveData.observe(this,openDescriptionScreenObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.messageLiveData.observe(viewLifecycleOwner,messageObserver)
         viewModel.savedBooksLiveData.observe(viewLifecycleOwner,savedBooksObserver)
        viewModel.placeHolderLiveData.observe(viewLifecycleOwner,placeHolderObserver)
        viewModel.lastReadBookLiveData.observe(viewLifecycleOwner,lastReadBookObserver)
        viewModel.recentViewVisibilityLiveData.observe(viewLifecycleOwner,recentViewVisibilityObserver)
        binding.apply {
            recyclerSaved.adapter = adapter
            recyclerSaved.layoutManager = GridLayoutManager(requireContext(),2)
        }

        adapter.setOnItemClickListener {
            viewModel.itemClicked(it)
        }

        attachClickListeners()
    }
    private val lastReadBookObserver = Observer<BookData> {
        binding.apply {
            Glide
                .with(requireContext())
                .load(it.coverUrl)
                .into(imageBook)
            txtAuthor.text = it.author
            txtTitle.text = it.title
        }
    }

    private val recentViewVisibilityObserver = Observer<Int> {
        binding.innerView2.visibility = it
        binding.imgRecent.visibility = it
    }
    private val placeHolderObserver = Observer<Int> {
        binding.placeHolder.visibility = it
    }

    private val messageObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val savedBooksObserver = Observer<List<BookData>> {
        adapter.submitList(it)
    }

    private val openDescriptionScreenObserver = Observer<BookData> {
        val bundle = Bundle()
        bundle.putSerializable("book",it)
        val screen = DescriptionScreen()
        screen.arguments = bundle
        replaceScreenAddToStack(screen)
    }

    private fun attachClickListeners(){
        binding.apply {
            innerView2.setOnClickListener {

                viewModel.openReadScreen()
            }
        }
    }
}